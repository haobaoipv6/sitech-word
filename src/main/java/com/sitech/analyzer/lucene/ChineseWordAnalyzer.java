package com.sitech.analyzer.lucene;

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

import com.sitech.analyzer.segmentation.Segmentation;
import com.sitech.analyzer.segmentation.SegmentationAlgorithm;
import com.sitech.analyzer.segmentation.SegmentationFactory;

/**
 * Lucene中文分析器
 * @author hb
 */
public class ChineseWordAnalyzer extends Analyzer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordAnalyzer.class);
    private Segmentation segmentation = null;
    
    public ChineseWordAnalyzer(){
        segmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
    }
    
    public ChineseWordAnalyzer(Segmentation segmentation) {
        this.segmentation = segmentation;
    }
    
    @Override
    protected TokenStreamComponents createComponents(String string, Reader reader) {
        Tokenizer tokenizer = new ChineseWordTokenizer(reader, segmentation);
        return new TokenStreamComponents(tokenizer);
    }
    
    public static void main(String args[]) throws IOException {
        Analyzer analyzer = new ChineseWordAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("text", "杨尚川是APDPlat应用级产品开发平台的作者");
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
            PositionIncrementAttribute positionIncrementAttribute = tokenStream.getAttribute(PositionIncrementAttribute.class);
            LOGGER.info(charTermAttribute.toString()+" ("+offsetAttribute.startOffset()+" - "+offsetAttribute.endOffset()+") "+positionIncrementAttribute.getPositionIncrement());
        }
        tokenStream = analyzer.tokenStream("text", "word是一个中文分词项目，作者是杨尚川，杨尚川的英文名叫ysc");
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
            PositionIncrementAttribute positionIncrementAttribute = tokenStream.getAttribute(PositionIncrementAttribute.class);
            LOGGER.info(charTermAttribute.toString()+" ("+offsetAttribute.startOffset()+" - "+offsetAttribute.endOffset()+") "+positionIncrementAttribute.getPositionIncrement());
        }
    }
}