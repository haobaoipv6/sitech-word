package com.sitech.analyzer.segmentation.impl;

import java.util.List;
import java.util.Map;

import com.sitech.analyzer.segmentation.SegmentationAlgorithm;
import com.sitech.analyzer.segmentation.SegmentationFactory;
import com.sitech.analyzer.segmentation.Word;

/**
 * 基于词典的双向最小匹配算法
 * Dictionary-based bidirectional minimum matching algorithm
 * @author hb
 */
public class BidirectionalMinimumMatching extends AbstractSegmentation{
    private static final AbstractSegmentation MIM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.MinimumMatching);
    private static final AbstractSegmentation RMIM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.ReverseMinimumMatching);
    @Override
    public List<Word> segImpl(final String text) {
        //逆向最小匹配
        List<Word> wordsRMIM = RMIM.seg(text);
        if(!ngramEnabled()){
            return wordsRMIM;
        }
        //正向最小匹配
        List<Word> wordsMIM = MIM.seg(text);
        //如果分词结果都一样，则直接返回结果
        if(wordsRMIM.size() == wordsMIM.size() 
                && wordsRMIM.equals(wordsMIM)){            
            return wordsRMIM;
        }
        
        //如果分词结果不一样，则利用ngram消歧
        Map<List<Word>, Float> words = ngram(wordsRMIM, wordsMIM);        
      
        //如果分值都一样，则选择逆向最小匹配
        float score = words.get(wordsRMIM);
        LOGGER.debug("逆向最小匹配："+wordsRMIM.toString()+" : ngram分值="+score);
        //最终结果
        List<Word> result = wordsRMIM;
        //最小分值
        float max = score;
        
        score = words.get(wordsMIM);
        LOGGER.debug("正向最小匹配："+wordsMIM.toString()+" : ngram分值="+score);
        //只有正向最小匹配的分值大于逆向最小匹配，才会被选择
        if(score > max){
            result = wordsMIM;
            max = score;
        }
        
        LOGGER.debug("最大分值："+max+", 消歧结果："+result);
        return result;
    }
    public static void main(String[] args){
        String text = "古人对于写文章有个基本要求，叫做“有物有序”。“有物”就是要有内容，“有序”就是要有条理。";
        if(args !=null && args.length == 1){
            text = args[0];
        }
        BidirectionalMinimumMatching m = new BidirectionalMinimumMatching();
        LOGGER.info(m.seg(text).toString());
    }
}
