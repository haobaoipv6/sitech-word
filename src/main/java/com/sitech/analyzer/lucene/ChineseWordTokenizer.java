package com.sitech.analyzer.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.recognition.StopWord;
import com.sitech.analyzer.segmentation.Segmentation;
import com.sitech.analyzer.segmentation.SegmentationAlgorithm;
import com.sitech.analyzer.segmentation.SegmentationFactory;
import com.sitech.analyzer.segmentation.Word;

/**
 * Lucene中文分词器
 * @author hb
 */
public class ChineseWordTokenizer extends Tokenizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordTokenizer.class);
    
    private final CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAttribute = addAttribute(OffsetAttribute.class);
    private final PositionIncrementAttribute positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);
    
    private Segmentation segmentation = null;
    private BufferedReader reader = null;
    private final Queue<Word> words = new LinkedTransferQueue<>();
    private int startOffset=0;
        
    public ChineseWordTokenizer(Reader input) {
        super(input);
        segmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
        reader = new BufferedReader(input);
    }   
    public ChineseWordTokenizer(Reader input, Segmentation segmentation) {
        super(input);
        this.segmentation = segmentation;
        reader = new BufferedReader(input);
    }
    private Word getWord() throws IOException {
        Word word = words.poll();
        if(word == null){
            String line;
            while( (line = reader.readLine()) != null ){
                words.addAll(segmentation.seg(line));
            }
            startOffset = 0;
            word = words.poll();
        }
        return word;
    }
    @Override
    public final boolean incrementToken() throws IOException {
        Word word = getWord();
        if (word != null) {
            int positionIncrement = 1;
            //忽略停用词
            while(StopWord.is(word.getText())){
                positionIncrement++;
                startOffset += word.getText().length();
                LOGGER.debug("忽略停用词："+word.getText());
                word = getWord();
                if(word == null){
                    return false;
                }
            }
            charTermAttribute.setEmpty().append(word.getText());
            offsetAttribute.setOffset(startOffset, startOffset+word.getText().length());
            positionIncrementAttribute.setPositionIncrement(positionIncrement);
            startOffset += word.getText().length();
            return true;
        }
        return false;
    }
}