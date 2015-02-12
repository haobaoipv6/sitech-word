package com.sitech.analyzer.corpus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.segmentation.Word;
import com.sitech.analyzer.util.AutoDetector;
import com.sitech.analyzer.util.IResource;
import com.sitech.analyzer.util.WordConfUtil;

/**
 * 三元语法模型
 * @author hb
 */
public class Trigram {
    private static final Logger LOGGER = LoggerFactory.getLogger(Trigram.class);
    private static final GramTrie GRAM_TRIE = new GramTrie();
    static{
        reload();
    }
    public static void reload(){
        AutoDetector.loadAndWatch(new IResource(){

            @Override
            public void clear() {
                GRAM_TRIE.clear();
            }

            @Override
            public void load(List<String> lines) {
                LOGGER.info("初始化trigram");
                int count=0;
                for(String line : lines){
                    try{
                        String[] attr = line.split("\\s+");
                        GRAM_TRIE.put(attr[0], Integer.parseInt(attr[1]));
                        count++;
                    }catch(Exception e){
                        LOGGER.error("错误的trigram数据："+line);
                    }
                }
                LOGGER.info("trigram初始化完毕，trigram数据条数："+count);
            }

            @Override
            public void add(String line) {
                try{
                    String[] attr = line.split("\\s+");
                    GRAM_TRIE.put(attr[0], Integer.parseInt(attr[1]));
                }catch(Exception e){
                    LOGGER.error("错误的trigram数据："+line);
                }
            }

            @Override
            public void remove(String line) {
                try{
                    String[] attr = line.split("\\s+");
                    GRAM_TRIE.remove(attr[0]);
                }catch(Exception e){
                    LOGGER.error("错误的trigram数据："+line);
                }
            }
        
        }, WordConfUtil.get("trigram.path", "classpath:trigram.txt"));
    }
    /**
     * 一次性计算多种分词结果的三元模型分值
     * @param sentences 多种分词结果
     * @return 分词结果及其对应的分值
     */
    public static Map<List<Word>, Float> trigram(List<Word>... sentences){
        Map<List<Word>, Float> map = new HashMap<>();
        //计算多种分词结果的分值
        for(List<Word> sentence : sentences){
            if(map.get(sentence) != null){
                //相同的分词结果只计算一次分值
                continue;
            }
            float score=0;
            //计算其中一种分词结果的分值
            if(sentence.size() > 2){
                for(int i=0; i<sentence.size()-2; i++){
                    String first = sentence.get(i).getText();
                    String second = sentence.get(i+1).getText();
                    String third = sentence.get(i+2).getText();
                    float trigramScore = getScore(first, second, third);
                    if(trigramScore > 0){
                        score += trigramScore;
                    }
                }
            }
            map.put(sentence, score);
        }
        
        return map;
    }
    /**
     * 计算分词结果的三元模型分值
     * @param words 分词结果
     * @return 三元模型分值
     */
    public static float trigram(List<Word> words){
        if(words.size() > 2){
            float score=0;
            for(int i=0; i<words.size()-2; i++){
                score += Trigram.getScore(words.get(i).getText(), words.get(i+1).getText(), words.get(i+2).getText());
            }
            return score;
        }
        return 0;
    }
    /**
     * 获取三个词前后紧挨着同时出现在语料库中的分值
     * @param first 第一个词
     * @param second 第二个词
     * @param third 第三个词
     * @return 同时出现的分值
     */
    public static float getScore(String first, String second, String third) {
        float value = GRAM_TRIE.get(first+":"+second+":"+third);
        if(value > 0){
            value = (float)Math.sqrt(value);
            LOGGER.debug("三元模型 "+first+":"+second+":"+third+" 获得分值："+value);
        }
        return value;
    }
}