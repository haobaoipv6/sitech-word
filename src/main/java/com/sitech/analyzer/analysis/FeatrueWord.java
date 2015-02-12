package com.sitech.analyzer.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sitech.analyzer.categroy.Punctuation;
import com.sitech.analyzer.util.RecognitionUtil;
import com.sitech.analyzer.util.Utils;

/**
 * 抽取文本特征词，无需词典。
 */
public class FeatrueWord {
	
	/**
	 * 获取文本分词
	 * @param text 文本或句子
	 * @param ngram 切词长度
	 * @return
	 */
    public static Map<String, Integer> get(String text, int ngram){
        Map<String, Integer> map = new HashMap<>();
        List<String> sentences = new ArrayList<>();
        for(String sentence : Punctuation.seg(text, false)){
            System.out.println("判断句子是否有英文单词："+sentence);
            int start=0;
            for(int i=0; i<sentence.length(); i++){
                if(RecognitionUtil.isEnglish(sentence.charAt(i))){
                    if(i>1 && !RecognitionUtil.isEnglish(sentence.charAt(i-1))){
                        sentences.add(sentence.substring(start,i));
                        start=i+1;
                    }else{
                        start++;
                    }
                }
                if(i==sentence.length()-1){
                    sentences.add(sentence.substring(start,i+1));
                }
            }
        }
        for(String sentence : sentences){
            System.out.println("分析文本："+sentence);
            int len = sentence.length()-ngram+1;
            for(int i=0; i<len; i++){
                String word = sentence.substring(i, i+ngram);
                System.out.print(word+" ");
                Integer count = map.get(word);
                if(count == null){
                    count = 1;
                }else{
                    count++;
                }
                map.put(word, count);
            }
        }
        System.out.println();
        return map;
    }
    public static void main(String[] args){
        Map<String, Integer> map = get("一个容易想到的思路，就是找到出现次数最多的词。如果某个词很重要，它应该在这篇文章中多次出现。于是，我们进行词频（Term Frequency，缩写为TF）统计.",2);
        
        for(Entry<String, Integer> entry : Utils.getSortedMapByValue(map)){
            System.out.println(entry.getKey()+"\t"+entry.getValue());
        }
    }
}
