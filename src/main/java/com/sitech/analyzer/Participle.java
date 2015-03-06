package com.sitech.analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;
import com.sitech.analyzer.algorithm.participle.ParticipleFactory;
import com.sitech.analyzer.bean.Word;
import com.sitech.analyzer.categroy.StopWord;

/**
 * 中文分词基础入口
 * 默认使用双向最大匹配算法
 * 也可指定其他分词算法
 */
public class Participle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Participle.class);
    
    /**
     * 对文本进行分词，保留停用词
     * 可指定其他分词算法
     * @param text 文本
     * @param segmentationAlgorithm 分词算法
     * @return 分词结果
     */
    public static List<Word> segWithStopWords(String text, SegmentationAlgorithm segmentationAlgorithm){
        return ParticipleFactory.getSegmentation(segmentationAlgorithm).seg(text);
    }
    /**
     * 对文本进行分词，保留停用词
     * 使用双向最大匹配算法
     * @param text 文本
     * @return 分词结果
     */
    public static List<Word> segWithStopWords(String text){
        return ParticipleFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching).seg(text);
    }
    /**
     * 对文本进行分词，移除停用词
     * 可指定其他分词算法
     * @param text 文本
     * @param segmentationAlgorithm 分词算法
     * @return 分词结果
     */
    public static List<Word> seg(String text, SegmentationAlgorithm segmentationAlgorithm){        
        List<Word> words = ParticipleFactory.getSegmentation(segmentationAlgorithm).seg(text);
        return filterStopWords(words);
    }
    /**
     * 对文本进行分词，移除停用词
     * 使用双向最大匹配算法
     * @param text 文本
     * @return 分词结果
     */
    public static List<Word> seg(String text){
        List<Word> words = ParticipleFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching).seg(text);
        return filterStopWords(words);
    }    
    /**
     * 移除停用词
     * @param words 词列表
     * @return 无停用词的词列表
     */
    private static List<Word> filterStopWords(List<Word> words){
        Iterator<Word> iter = words.iterator();
        while(iter.hasNext()){
            Word word = iter.next();
            if(StopWord.is(word.getText())){
                //去除停用词
                LOGGER.debug("去除停用词："+word.getText());
                iter.remove();
            }
        }
        return words;
    }
    /**
     * 对文件进行分词，保留停用词
     * 可指定其他分词算法
     * @param input 输入文件
     * @param output 输出文件
     * @param segmentationAlgorithm 分词算法
     * @throws Exception 
     */
    public static void segWithStopWords(File input, File output, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        seg(input, output, false, segmentationAlgorithm);
    }
    /**
     * 对文件进行分词，保留停用词
     * 使用双向最大匹配算法
     * @param input 输入文件
     * @param output 输出文件
     * @throws Exception 
     */
    public static void segWithStopWords(File input, File output) throws Exception{
        seg(input, output, false, SegmentationAlgorithm.BidirectionalMaximumMatching);
    }
    /**
     * 对文件进行分词，移除停用词
     * 可指定其他分词算法
     * @param input 输入文件
     * @param output 输出文件
     * @param segmentationAlgorithm 分词算法
     * @throws Exception 
     */
    public static void seg(File input, File output, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        seg(input, output, true, segmentationAlgorithm);
    }
    /**
     * 对文件进行分词，移除停用词
     * 使用双向最大匹配算法
     * @param input 输入文件
     * @param output 输出文件
     * @throws Exception 
     */
    public static void seg(File input, File output) throws Exception{
        seg(input, output, true, SegmentationAlgorithm.BidirectionalMaximumMatching);
    }
    /**
     * 
     * 对文件进行分词
     * @param input 输入文件
     * @param output 输出文件
     * @param removeStopWords 是否移除停用词
     * @param segmentationAlgorithm 分词算法
     * @throws Exception 
     */
    private static void seg(File input, File output, boolean removeStopWords, SegmentationAlgorithm segmentationAlgorithm) throws Exception{
        LOGGER.info("开始对文件进行分词："+input.toString());
        float max=(float)Runtime.getRuntime().maxMemory()/1000000;
        float total=(float)Runtime.getRuntime().totalMemory()/1000000;
        float free=(float)Runtime.getRuntime().freeMemory()/1000000;
        String pre="执行之前剩余内存:"+max+"-"+total+"+"+free+"="+(max-total+free);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input),"utf-8"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"utf-8"))){
            long size = Files.size(input.toPath());
            LOGGER.info("size:"+size);
            LOGGER.info("文件大小："+(float)size/1024/1024+" MB");
            int textLength=0;
            int progress=0;
            long start = System.currentTimeMillis();
            String line = null;
            while((line = reader.readLine()) != null){
                if("".equals(line.trim())){
                    writer.write("\n");
                    continue;
                }
                textLength += line.length();
                List<Word> words = null;
                if(removeStopWords){
                    words = seg(line, segmentationAlgorithm);
                }else{
                    words = segWithStopWords(line, segmentationAlgorithm);
                }
                if(words == null){
                    continue;
                }
                for(Word word : words){
                    writer.write(word.getText()+" ");
                }
                writer.write("\n");
                progress += line.length();
                if( progress > 500000){
                    progress = 0;
                    LOGGER.info("分词进度："+(int)((float)textLength*2/size*100)+"%");
                }
            }
            long cost = System.currentTimeMillis() - start;
            float rate = textLength/cost;
            LOGGER.info("字符数目："+textLength);
            LOGGER.info("分词耗时："+cost+" 毫秒");
            LOGGER.info("分词速度："+rate+" 字符/毫秒");
        }
        max=(float)Runtime.getRuntime().maxMemory()/1000000;
        total=(float)Runtime.getRuntime().totalMemory()/1000000;
        free=(float)Runtime.getRuntime().freeMemory()/1000000;
        String post="执行之后剩余内存:"+max+"-"+total+"+"+free+"="+(max-total+free);
        LOGGER.info(pre);
        LOGGER.info(post);
        LOGGER.info("将文件 "+input.toString()+" 的分词结果保存到文件 "+output);
    }
}