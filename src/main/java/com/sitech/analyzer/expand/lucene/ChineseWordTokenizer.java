package com.sitech.analyzer.expand.lucene;

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

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;
import com.sitech.analyzer.algorithm.participle.IParticiple;
import com.sitech.analyzer.algorithm.participle.ParticipleFactory;
import com.sitech.analyzer.bean.Word;
import com.sitech.analyzer.categroy.StopWord;

/**
 * Lucene中文分词器
 * @author hb
 */
public class ChineseWordTokenizer extends Tokenizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordTokenizer.class);
    
    private final CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAttribute = addAttribute(OffsetAttribute.class);
    private final PositionIncrementAttribute positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);
    
    private IParticiple segmentation = null;
    private BufferedReader reader = null;
    private final Queue<Word> words = new LinkedTransferQueue<>();
    private int startOffset=0;
        
    public ChineseWordTokenizer(Reader input) {
        super(input);
        segmentation = ParticipleFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
        reader = new BufferedReader(input);
    }   
    public ChineseWordTokenizer(Reader input, IParticiple segmentation) {
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