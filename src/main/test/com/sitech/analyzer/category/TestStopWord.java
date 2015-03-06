package com.sitech.analyzer.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.categroy.StopWord;

public class TestStopWord {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestStopWord.class);
	
	public static void main(String[] args){
        LOGGER.info("停用词：");
        int i=1;
        for(String w : StopWord.getStopwords()){
            LOGGER.info((i++)+" : "+w);
        }
    }
}
