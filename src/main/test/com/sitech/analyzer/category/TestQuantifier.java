package com.sitech.analyzer.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.categroy.Quantifier;

/**
 * 
 * @author haobao
 */
public class TestQuantifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPunctuation.class);
	
	public static void main(String[] args){
        int i=1;
        for(char quantifier : Quantifier.getQuantifiers()){
            LOGGER.info((i++)+" : "+quantifier);
        }
    }
}
