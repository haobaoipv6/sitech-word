package com.sitech.analyzer.segmentation.impl;

import java.util.List;
import java.util.Map;

import com.sitech.analyzer.segmentation.SegmentationAlgorithm;
import com.sitech.analyzer.segmentation.SegmentationFactory;
import com.sitech.analyzer.segmentation.Word;

/**
 * 基于词典的双向最大匹配算法
 * Dictionary-based bidirectional maximum matching algorithm
 * @author hb
 */
public class BidirectionalMaximumMatching extends AbstractSegmentation{
    private static final AbstractSegmentation MM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.MaximumMatching);
    private static final AbstractSegmentation RMM = (AbstractSegmentation)SegmentationFactory.getSegmentation(SegmentationAlgorithm.ReverseMaximumMatching);
    @Override
    public List<Word> segImpl(final String text) {
        //逆向最大匹配
        List<Word> wordsRMM = RMM.seg(text);
        if(!ngramEnabled()){
            return wordsRMM;
        }
        //正向最大匹配
        List<Word> wordsMM = MM.seg(text);
        //如果分词结果都一样，则直接返回结果
        if(wordsRMM.size() == wordsMM.size() 
                && wordsRMM.equals(wordsMM)){            
            return wordsRMM;
        }
        
        //如果分词结果不一样，则利用ngram消歧
        Map<List<Word>, Float> words = ngram(wordsRMM, wordsMM);        
      
        //如果分值都一样，则选择逆向最大匹配
        float score = words.get(wordsRMM);
        LOGGER.debug("逆向最大匹配："+wordsRMM.toString()+" : ngram分值="+score);
        //最终结果
        List<Word> result = wordsRMM;
        //最大分值
        float max = score;
        
        score = words.get(wordsMM);
        LOGGER.debug("正向最大匹配："+wordsMM.toString()+" : ngram分值="+score);
        //只有正向最大匹配的分值大于逆向最大匹配，才会被选择
        if(score > max){
            result = wordsMM;
            max = score;
        }
        
        LOGGER.debug("最大分值："+max+", 消歧结果："+result);
        return result;
    }
    public static void main(String[] args){
        String text = "APDPlat的雏形可以追溯到2008年，并于4年后即2012年4月9日在GITHUB开源 。APDPlat在演化的过程中，经受住了众多项目的考验，一直追求简洁优雅，一直对架构、设计和代码进行重构优化。 ";
        if(args !=null && args.length == 1){
            text = args[0];
        }
        BidirectionalMaximumMatching m = new BidirectionalMaximumMatching();
        LOGGER.info(m.seg(text).toString());
    }
}
