package com.sitech.analyzer.expand.elasticsearch;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;
import com.sitech.analyzer.algorithm.participle.IParticiple;
import com.sitech.analyzer.algorithm.participle.ParticipleFactory;
import com.sitech.analyzer.expand.lucene.ChineseWordAnalyzer;

/**
 * 中文分析器工厂
 * @author hb
 */
public class ChineseWordAnalyzerProvider extends AbstractIndexAnalyzerProvider<ChineseWordAnalyzer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordAnalyzerProvider.class);
    private final ChineseWordAnalyzer analyzer;
    @Inject
    public ChineseWordAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        String segAlgorithm = settings.get("segAlgorithm");
        if(segAlgorithm != null){
            LOGGER.info("analyzer使用指定分词算法："+segAlgorithm);
            IParticiple segmentation = ParticipleFactory.getSegmentation(SegmentationAlgorithm.valueOf(segAlgorithm));
            analyzer = new ChineseWordAnalyzer(segmentation);
        }else{
            LOGGER.info("没有为word analyzer指定segAlgorithm参数");
            LOGGER.info("analyzer使用默认分词算法："+SegmentationAlgorithm.BidirectionalMaximumMatching);
            analyzer = new ChineseWordAnalyzer();
        }
    }
    @Override
    public ChineseWordAnalyzer get() {
        return this.analyzer;
    }
}