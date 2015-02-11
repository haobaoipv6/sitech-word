package com.sitech.analyzer.dictionary;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.util.RecognitionTool;
import com.sitech.analyzer.util.Utils;

/**
 * 词典工具
 * 1、把多个词典合并为一个并规范清理
 * 词长度：只保留大于等于2并且小于等于4的长度的词
 * 识别功能： 移除能识别的词
 * 移除非中文词：防止大量无意义或特殊词混入词典
 * 2、移除词典中的短语结构
 * @author hb
 */
public class DictionaryTools {    
    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryTools.class);
    public static void main(String[] args) throws IOException{
        List<String> sources = new ArrayList<>();
        sources.add("src/main/resources/dic.txt");
        sources.add("target/dic.txt");
        String target = "src/main/resources/dic.txt";
        merge(sources, target);
    }    
    /**
     * 移除词典中的短语结构
     * @param phrasePath
     * @param dicPath
     */
    public static void removePhraseFromDic(String phrasePath, String dicPath) {
        try{
            Set<String> set = new HashSet<>();
            List<String> phrases = Files.readAllLines(Paths.get(phrasePath), Charset.forName("utf-8"));            
            for(String phrase : phrases){
                String[] attr = phrase.split("=");
                if(attr != null && attr.length == 2){
                    set.add(attr[0]);
                }
            }
            List<String> list = new ArrayList<>();
            List<String> words = Files.readAllLines(Paths.get(dicPath), Charset.forName("utf-8"));
            int len = words.size();
            for(String word : words){
                if(!set.contains(word)){
                    list.add(word);
                }
            }        
            words.clear();
            set.clear();
            Files.write(Paths.get(dicPath), list, Charset.forName("utf-8"));        
            len = len - list.size();
            LOGGER.info("移除短语结构数目："+len);
        }catch(Exception e){
            LOGGER.error("移除短语结构失败：", e);
        }
    }
    /**
     * 把多个词典合并为一个
     * @param sources 多个待词典
     * @param target 合并后的词典
     * @throws IOException 
     */
    public static void merge(List<String> sources, String target) throws IOException{
        List<String> lines = new ArrayList<>();
        //读取所有需要合并的词典
        for(String source : sources){
            lines.addAll(Files.readAllLines(Paths.get(source), Charset.forName("utf-8")));
        }
        Set<String> set = new HashSet<>();
        for(String line : lines){
            line = line.trim();
            // 词长度：大于等于2并且小于等于4
            // 识别功能 能识别的词 就不用放到词典中了，没必要多此一举
            //至少要两个中文字符，防止大量无意义或特殊词混入词典
            if(line.length() > 4 
                    || line.length() < 2
                    || !Utils.isChineseCharAndLengthAtLeastTwo(line)
                    || RecognitionTool.recog(line)){
                LOGGER.debug("过滤："+line);
                continue;
            }
            set.add(line);
        }
        LOGGER.info("合并词数："+lines.size());
        LOGGER.info("保留词数："+set.size());
        lines.clear();
        List<String> list = new ArrayList<>();
        list.addAll(set);
        set.clear();
        Collections.sort(list);
        Files.write(Paths.get(target), list, Charset.forName("utf-8"));
    }
}
