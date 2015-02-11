package com.sitech.analyzer.elasticsearch;

import org.elasticsearch.common.inject.AbstractModule;

/**
 * 中文分词索引分析模块
 * @author hb
 */
public class ChineseWordIndicesAnalysisModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ChineseWordIndicesAnalysis.class).asEagerSingleton();
    }
}