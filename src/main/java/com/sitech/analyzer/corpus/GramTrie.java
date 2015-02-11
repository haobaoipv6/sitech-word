package com.sitech.analyzer.corpus;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ngram数据结构，高效存储，快速搜索
 * 使用前缀树来实现
 * 为前缀树的一级节点建立索引（比二分查找要快）
 * @author hb
 */
public class GramTrie{
    private static final Logger LOGGER = LoggerFactory.getLogger(GramTrie.class);
    //词表的首字母数量在一个可控范围内，默认值为12000
    private static final int INDEX_LENGTH = 12000;
    private final TrieNode[] ROOT_NODES_INDEX = new TrieNode[INDEX_LENGTH];
    
    public void clear() {
        for(int i=0; i<INDEX_LENGTH; i++){
            ROOT_NODES_INDEX[i] = null;
        }
    }
    /**
     * 统计根节点冲突情况及预分配的数组空间利用情况
     */
    public void showConflict(){
        int emptySlot=0;
        //key:冲突长度 value:冲突个数
        Map<Integer, Integer> map = new HashMap<>();
        for(TrieNode node : ROOT_NODES_INDEX){
            if(node == null){
                emptySlot++;
            }else{
                int i=0;
                while((node = node.getSibling()) != null){
                    i++;
                }
                if(i > 0){
                    Integer count = map.get(i);
                    if(count == null){
                        count = 1;
                    }else{
                        count++;
                    }
                    map.put(i, count);
                }
            }
        }
        int count=0;
        for(int key : map.keySet()){
            int value = map.get(key);
            count += key*value;
            LOGGER.info("冲突次数为："+key+" 的元素个数："+value);
        }
        LOGGER.info("冲突次数："+count);
        LOGGER.info("总槽数："+INDEX_LENGTH);
        LOGGER.info("用槽数："+(INDEX_LENGTH-emptySlot));
        LOGGER.info("使用率："+(float)(INDEX_LENGTH-emptySlot)/INDEX_LENGTH*100+"%");
        LOGGER.info("剩槽数："+emptySlot);
    }
    /**
     * 获取字符对应的根节点
     * 如果节点不存在
     * 则增加根节点后返回新增的节点
     * @param character 字符
     * @return 字符对应的根节点
     */
    private TrieNode getRootNodeIfNotExistThenCreate(char character){
        TrieNode trieNode = getRootNode(character);
        if(trieNode == null){
            trieNode = new TrieNode(character);
            addRootNode(trieNode);
        }
        return trieNode;
    }
    /**
     * 新增一个根节点
     * @param rootNode 根节点
     */
    private void addRootNode(TrieNode rootNode){
        //计算节点的存储索引
        int index = rootNode.getCharacter()%INDEX_LENGTH;
        //检查索引是否和其他节点冲突
        TrieNode existTrieNode = ROOT_NODES_INDEX[index];
        if(existTrieNode != null){
            //有冲突，将冲突节点附加到当前节点之后
            rootNode.setSibling(existTrieNode);
        }
        //新增的节点总是在最前
        ROOT_NODES_INDEX[index] = rootNode;
    }
    /**
     * 获取字符对应的根节点
     * 如果不存在，则返回NULL
     * @param character 字符
     * @return 字符对应的根节点
     */
    private TrieNode getRootNode(char character){
        //计算节点的存储索引
        int index = character%INDEX_LENGTH;
        TrieNode trieNode = ROOT_NODES_INDEX[index];
        while(trieNode != null && character != trieNode.getCharacter()){
            //如果节点和其他节点冲突，则需要链式查找
            trieNode = trieNode.getSibling();
        }
        return trieNode;
    }
    public int get(String item){
        return get(item, 0, item.length());
    }
    public int get(String item, int start, int length){
        if(start < 0 || length < 1){
            return 0;
        }
        if(item == null || item.length() < length){
            return 0;
        }
        //从根节点开始查找
        //获取根节点
        TrieNode node = getRootNode(item.charAt(start));
        if(node == null){
            //不存在根节点，结束查找
            return 0;
        }
        //存在根节点，继续查找
        for(int i=1;i<length;i++){
            char character = item.charAt(i+start);
            TrieNode child = node.getChild(character);
            if(child == null){
                //未找到匹配节点
                return 0;
            }else{
                //找到节点，继续往下找
                node = child;
            }
        }
        if(node.isTerminal()){
            return node.getScore();
        }
        return 0;
    }
    /**
     * 移除ngram
     * @param item 
     */
    public void remove(String item) {
        if(item == null || item.isEmpty()){
            return;
        }
        LOGGER.debug("移除ngram："+item);
        //从根节点开始查找
        //获取根节点
        TrieNode node = getRootNode(item.charAt(0));
        if(node == null){
            //不存在根节点，结束查找
            LOGGER.debug("ngram不存在："+item);
            return;
        }
        int length = item.length();
        //存在根节点，继续查找
        for(int i=1;i<length;i++){
            char character = item.charAt(i);
            TrieNode child = node.getChild(character);
            if(child == null){
                //未找到匹配节点
                LOGGER.debug("ngram不存在："+item);
                return;
            }else{
                //找到节点，继续往下找
                node = child;
            }
        }
        if(node.isTerminal()){
            //设置为非叶子节点，效果相当于移除ngram
            node.setTerminal(false);
            node.setScore(0);
            LOGGER.debug("成功移除ngram："+item);
        }else{
            LOGGER.debug("ngram不存在："+item);
        }
    }
    
    public void put(String item, int score){
        //去掉首尾空白字符
        item=item.trim();
        int len = item.length();
        if(len < 1){
            //长度小于1则忽略
            return;
        }
        //从根节点开始添加
        //获取根节点
        TrieNode node = getRootNodeIfNotExistThenCreate(item.charAt(0));
        for(int i=1;i<len;i++){
            char character = item.charAt(i);
            TrieNode child = node.getChildIfNotExistThenCreate(character);
            //改变顶级节点
            node = child;
        }
        //设置终结字符，表示从根节点遍历到此是一个合法的词
        node.setTerminal(true);
        //设置分值
        node.setScore(score);
    }
    private static class TrieNode implements Comparable{
        private char character;
        private int score;
        private boolean terminal;
        private TrieNode sibling;
        private TrieNode[] children = new TrieNode[0];
        public TrieNode(char character){
            this.character = character;
        }
        public boolean isTerminal() {
            return terminal;
        }
        public void setTerminal(boolean terminal) {
            this.terminal = terminal;
        }        
        public char getCharacter() {
            return character;
        }
        public void setCharacter(char character) {
            this.character = character;
        }
        public int getScore() {
            return score;
        }
        public void setScore(int score) {
            this.score = score;
        }
        public TrieNode getSibling() {
            return sibling;
        }
        public void setSibling(TrieNode sibling) {
            this.sibling = sibling;
        }
        public Collection<TrieNode> getChildren() {
            return Arrays.asList(children);            
        }
        /**
         * 利用二分搜索算法从有序数组中找到特定的节点
         * @param character 待查找节点
         * @return NULL OR 节点数据
         */
        public TrieNode getChild(char character) {
            int index = Arrays.binarySearch(children, character);
            if(index >= 0){
                return children[index];
            }
            return null;
        }        
        public TrieNode getChildIfNotExistThenCreate(char character) {
            TrieNode child = getChild(character);
            if(child == null){
                child = new TrieNode(character);
                addChild(child);
            }
            return child;
        }
        public void addChild(TrieNode child) {
            children = insert(children, child);
        }
        /**
         * 将一个字符追加到有序数组
         * @param array 有序数组
         * @param element 字符
         * @return 新的有序数字
         */
        private TrieNode[] insert(TrieNode[] array, TrieNode element){
            int length = array.length;
            if(length == 0){
                array = new TrieNode[1];
                array[0] = element;
                return array;
            }
            TrieNode[] newArray = new TrieNode[length+1];
            boolean insert=false;
            for(int i=0; i<length; i++){
                if(element.getCharacter() <= array[i].getCharacter()){
                    //新元素找到合适的插入位置
                    newArray[i]=element;
                    //将array中剩下的元素依次加入newArray即可退出比较操作
                    System.arraycopy(array, i, newArray, i+1, length-i);
                    insert=true;
                    break;
                }else{
                    newArray[i]=array[i];
                }
            }
            if(!insert){
                //将新元素追加到尾部
                newArray[length]=element;
            }
            return newArray;
        }
        /**
         * 注意这里的比较对象是char
         * @param o char
         * @return 
         */
        @Override
        public int compareTo(Object o) {
            return this.getCharacter()-(char)o;
        }
    }    
    public void show(char character){
        show(getRootNode(character), "");
    }
    public void show(){
        for(TrieNode node : ROOT_NODES_INDEX){
            if(node != null){
                show(node, "");
            }
        }
    }
    private void show(TrieNode node, String indent){
        if(node.isTerminal()){
            LOGGER.info(indent+node.getCharacter()+"="+node.getScore()+"(T)");
        }else{
            LOGGER.info(indent+node.getCharacter());
        }        
        for(TrieNode item : node.getChildren()){
            show(item,indent+"\t");
        }
    }
    public static void main(String[] args){
        GramTrie trie = new GramTrie();
        trie.put("杨尚川", 100);
        trie.put("杨尚喜", 99);
        trie.put("杨尚丽", 99);
        trie.show();
        LOGGER.info("杨尚川："+trie.get("杨尚川"));
        LOGGER.info("杨尚喜："+trie.get("杨尚喜"));
        LOGGER.info("杨尚丽："+trie.get("杨尚丽"));
    }
}