package com.sitech.analyzer.expand.elasticsearch;

import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;
import java.util.Collection;

/**
 * 中文分词组件（word）的ElasticSearch插件
 * @author hb
 */
public class ChineseWordPlugin extends AbstractPlugin {
    @Override
    public String name() {
        return "word";
    }
    @Override
    public String description() {
        return "中文分词组件（word）";
    }
    @Override
    public Collection<Class<? extends Module>> modules() {
        return ImmutableList.<Class<? extends Module>>of(ChineseWordIndicesAnalysisModule.class);
    }
    public void onModule(AnalysisModule module) {
        module.addProcessor(new ChineseWordAnalysisBinderProcessor());
    }
}