package com.sitech.analyzer.expand.lucene;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;
import com.sitech.analyzer.algorithm.participle.IParticiple;
import com.sitech.analyzer.algorithm.participle.ParticipleFactory;

/**
 * Lucene中文分析器
 * @author hb
 */
public class ChineseWordAnalyzer extends Analyzer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordAnalyzer.class);
    private IParticiple segmentation = null;
    
    public ChineseWordAnalyzer(){
        segmentation = ParticipleFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
    }
    
    public ChineseWordAnalyzer(IParticiple segmentation) {
        this.segmentation = segmentation;
    }
    
    @Override
    protected TokenStreamComponents createComponents(String string, Reader reader) {
        Tokenizer tokenizer = new ChineseWordTokenizer(reader, segmentation);
        return new TokenStreamComponents(tokenizer);
    }
    
    public static void main(String args[]) throws IOException {
        Analyzer analyzer = new ChineseWordAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("text", "Topic Modeling是一种文本挖掘的方法");
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
            PositionIncrementAttribute positionIncrementAttribute = tokenStream.getAttribute(PositionIncrementAttribute.class);
            LOGGER.info(charTermAttribute.toString()+" ("+offsetAttribute.startOffset()+" - "+offsetAttribute.endOffset()+") "+positionIncrementAttribute.getPositionIncrement());
        }
        tokenStream = analyzer.tokenStream("text", "Topic Modeling是一种文本挖掘的方法");
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
            PositionIncrementAttribute positionIncrementAttribute = tokenStream.getAttribute(PositionIncrementAttribute.class);
            LOGGER.info(charTermAttribute.toString()+" ("+offsetAttribute.startOffset()+" - "+offsetAttribute.endOffset()+") "+positionIncrementAttribute.getPositionIncrement());
        }
    }
}