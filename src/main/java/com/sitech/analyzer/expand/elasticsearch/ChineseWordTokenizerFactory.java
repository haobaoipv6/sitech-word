package com.sitech.analyzer.expand.elasticsearch;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;
import com.sitech.analyzer.algorithm.participle.IParticiple;
import com.sitech.analyzer.algorithm.participle.ParticipleFactory;
import com.sitech.analyzer.expand.lucene.ChineseWordTokenizer;

/**
 * 中文分词器工厂
 * @author hb
 */
public class ChineseWordTokenizerFactory extends AbstractTokenizerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordTokenizerFactory.class);
    private final IParticiple segmentation;
    @Inject
    public ChineseWordTokenizerFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        String segAlgorithm = settings.get("segAlgorithm");
        if(segAlgorithm != null){
            LOGGER.info("tokenizer使用指定分词算法："+segAlgorithm);
            segmentation = ParticipleFactory.getSegmentation(SegmentationAlgorithm.valueOf(segAlgorithm));
        }else{
            LOGGER.info("没有为word tokenizer指定segAlgorithm参数");
            LOGGER.info("tokenizer使用默认分词算法："+SegmentationAlgorithm.BidirectionalMaximumMatching);
            segmentation = ParticipleFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
        }
    }
    @Override
    public Tokenizer create(Reader reader) {
        return new ChineseWordTokenizer(reader, segmentation);
    }
}