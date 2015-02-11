package com.sitech.analyzer.elasticsearch;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

import java.io.Reader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.lucene.ChineseWordAnalyzer;
import com.sitech.analyzer.lucene.ChineseWordTokenizer;
import com.sitech.analyzer.segmentation.Segmentation;
import com.sitech.analyzer.segmentation.SegmentationAlgorithm;
import com.sitech.analyzer.segmentation.SegmentationFactory;

/**
 * 中文分词索引分析组件
 * @author hb
 */
public class ChineseWordIndicesAnalysis extends AbstractComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChineseWordIndicesAnalysis.class);
    private Segmentation analyzerSegmentation = null;
    private Segmentation tokenizerSegmentation = null;
    @Inject
    public ChineseWordIndicesAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);
        Object index = settings.getAsStructuredMap().get("index");
        if(index != null && index instanceof Map){
            Map indexMap = (Map)index;
            Object analysis = indexMap.get("analysis");
            if(analysis != null && analysis instanceof Map){
                Map analysisMap = (Map)analysis;
                Object analyzer = analysisMap.get("analyzer");
                Object tokenizer = analysisMap.get("tokenizer");
                if(analyzer != null && analyzer instanceof Map){
                    Map analyzerMap = (Map)analyzer;
                    Object _default = analyzerMap.get("default");
                    if(_default != null && _default instanceof Map){
                        Map _defaultMap = (Map)_default;
                        Object type = _defaultMap.get("type");
                        Object segAlgorithm = _defaultMap.get("segAlgorithm");
                        if(segAlgorithm != null && type != null && "word".equals(type.toString())){
                            LOGGER.info("analyzer使用指定分词算法："+segAlgorithm.toString());
                            analyzerSegmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.valueOf(segAlgorithm.toString()));
                        }
                    }
                }
                if(tokenizer != null && tokenizer instanceof Map){
                    Map tokenizerMap = (Map)tokenizer;
                    Object _default = tokenizerMap.get("default");
                    if(_default != null && _default instanceof Map){
                        Map _defaultMap = (Map)_default;
                        Object type = _defaultMap.get("type");
                        Object segAlgorithm = _defaultMap.get("segAlgorithm");
                        if(segAlgorithm != null && type != null && "word".equals(type.toString())){
                            LOGGER.info("tokenizer使用指定分词算法："+segAlgorithm.toString());
                            tokenizerSegmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.valueOf(segAlgorithm.toString()));
                        }
                    }
                }
            }            
        }
        if(analyzerSegmentation == null){
            LOGGER.info("没有为word analyzer指定segAlgorithm参数");
            LOGGER.info("analyzer使用默认分词算法："+SegmentationAlgorithm.BidirectionalMaximumMatching);
            analyzerSegmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
        }
        if(tokenizerSegmentation == null){
            LOGGER.info("没有为word tokenizer指定segAlgorithm参数");
            LOGGER.info("tokenizer使用默认分词算法："+SegmentationAlgorithm.BidirectionalMaximumMatching);
            tokenizerSegmentation = SegmentationFactory.getSegmentation(SegmentationAlgorithm.BidirectionalMaximumMatching);
        }
        // 注册分析器
        indicesAnalysisService.analyzerProviderFactories()
                .put("word", new PreBuiltAnalyzerProviderFactory("word", AnalyzerScope.GLOBAL, 
                        new ChineseWordAnalyzer(analyzerSegmentation)));
        // 注册分词器
        indicesAnalysisService.tokenizerFactories()
                .put("word", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "word";
            }
            @Override
            public Tokenizer create(Reader reader) {
                return new ChineseWordTokenizer(reader, tokenizerSegmentation);
            }
        }));        
    }
}