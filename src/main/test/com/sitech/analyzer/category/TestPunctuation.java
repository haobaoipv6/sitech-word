package com.sitech.analyzer.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.categroy.Punctuation;

/**
 * test判断一个字符是否是标点符号
 * @author hb
 */
public class TestPunctuation {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPunctuation.class);
	
	public static void main(String[] args){
        String text= "以PLSA和LDA为代表的文本语言模型是当今统计自然语言处理研究的热点问题。这类语言模型一般都是对文本的生成过程提出自己的概率图模型，然后利用观察到的语料数据对模型参数做估计。有了语言模型和相应的模型参数，我们可以有很多重要的应用，比如文本特征降维、文本主题分析等等。本文主要介绍文本分析的三类参数估计方法-最大似然估计MLE、最大后验概率估计MAP及贝叶斯估计。";
        for(String s : Punctuation.seg(text, true)){
            LOGGER.info(s);
        }
    }
}
