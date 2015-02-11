package com.sitech.analyzer.elasticsearch;

import org.elasticsearch.index.analysis.AnalysisModule;

/**
 * 中文分词组件注册
 * @author hb
 */
public class ChineseWordAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {
    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("word", ChineseWordAnalyzerProvider.class);
    }
    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
        tokenizersBindings.processTokenizer("word", ChineseWordTokenizerFactory.class);
    }
}