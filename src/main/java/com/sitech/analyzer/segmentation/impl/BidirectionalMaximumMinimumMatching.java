package com.sitech.analyzer.segmentation.impl;

import java.util.List;
import java.util.Map;

import com.sitech.analyzer.segmentation.SegmentationAlgorithm;
import com.sitech.analyzer.segmentation.SegmentationFactory;
import com.sitech.analyzer.segmentation.Word;

/**
 * 基于词典的双向最大最小匹配算法
 * Dictionary-based bidirectional maximum minimum matching algorithm
 * 利用ngram从
 * 逆向最大匹配、正向最大匹配、逆向最小匹配、正向最小匹配
 * 4种切分结果中选择一种最好的分词结果
 * 如果分值都一样，则选择逆向最大匹配
 * 实验表明，对于汉语来说，逆向最大匹配算法比(正向)最大匹配算法更有效
 * @author hb
 */
public class BidirectionalMaximumMinimumMatching extends AbstractSegmentation{
    private static final AbstractSegmentation MM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.MaximumMatching);
    private static final AbstractSegmentation RMM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.ReverseMaximumMatching);
    private static final AbstractSegmentation MIM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.MinimumMatching);
    private static final AbstractSegmentation RMIM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.ReverseMinimumMatching);
    @Override
    public List<Word> segImpl(final String text) {
        //逆向最大匹配为默认选择，如果分值都一样的话
        List<Word> wordsRMM = RMM.seg(text);
        if(!ngramEnabled()){
            return wordsRMM;
        }
        //正向最大匹配
        List<Word> wordsMM = MM.seg(text);
        //逆向最小匹配
        List<Word> wordsRMIM = RMIM.seg(text);
        //正向最小匹配
        List<Word> wordsMIM = MIM.seg(text);
        //如果分词结果都一样，则直接返回结果
        if(wordsRMM.size() == wordsMM.size()
                && wordsRMM.size() == wordsRMIM.size()
                && wordsRMM.size() == wordsMIM.size()
                && wordsRMM.equals(wordsMM)
                && wordsRMM.equals(wordsRMIM)
                && wordsRMM.equals(wordsMIM)){            
            return wordsRMM;
        }
        
        //如果分词结果不一样，则利用ngram消歧
        Map<List<Word>, Float> words = ngram(wordsRMM, wordsMM, wordsRMIM, wordsMIM);        
      
        //如果分值都一样，则选择逆向最大匹配
        float score = words.get(wordsRMM);
        LOGGER.debug("逆向最大匹配："+wordsRMM.toString()+" : ngram分值="+score);
        //最终结果
        List<Word> result = wordsRMM;
        //最大分值
        float max = score;
        
        score = words.get(wordsMM);
        LOGGER.debug("正向最大匹配："+wordsMM.toString()+" : ngram分值="+score);
        if(score > max){
            result = wordsMM;
            max = score;
        }
        
        score = words.get(wordsRMIM);
        LOGGER.debug("逆向最小匹配："+wordsRMIM.toString()+" : ngram分值="+score);
        if(score > max){
            result = wordsRMIM;
            max = score;
        }
        
        score = words.get(wordsMIM);
        LOGGER.debug("正向最小匹配："+wordsMIM.toString()+" : ngram分值="+score);
        if(score > max){
            result = wordsMIM;
            max = score;
        }
        
        LOGGER.debug("最大分值："+max+", 消歧结果："+result);
        return result;
    }
    public static void main(String[] args){
        String text = "Hadoop是大数据的核心技术之一，而Nutch集Hadoop之大成，是Hadoop的源头。学习Hadoop，没有数据怎么办？用Nutch抓！学了Hadoop的Map Reduce以及HDFS，没有实用案例怎么办？学习Nutch！Nutch的很多代码是用Map Reduce和HDFS写的，哪里还能找到比Nutch更好的Hadoop应用案例呢？";
        if(args !=null && args.length == 1){
            text = args[0];
        }
        BidirectionalMaximumMinimumMatching m = new BidirectionalMaximumMinimumMatching();
        LOGGER.info(m.seg(text).toString());
    }
}