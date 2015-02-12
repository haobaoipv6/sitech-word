package com.sitech.analyzer.expand.elasticsearch;

import static org.elasticsearch.common.settings.ImmutableSettings.Builder.EMPTY_SETTINGS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.env.Environment;
import org.elasticsearch.env.EnvironmentModule;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexNameModule;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.junit.Test;

import com.sitech.analyzer.expand.elasticsearch.ChineseWordAnalysisBinderProcessor;
import com.sitech.analyzer.expand.elasticsearch.ChineseWordTokenizerFactory;
import com.sitech.analyzer.expand.lucene.ChineseWordAnalyzer;

/**
 * ElasticSearch中文分词索引分析单元测试
 * @author hb
 */
public class ChineseWordIndicesAnalysisTest {

    @Test
    public void testChineseWordIndicesAnalysis() throws IOException {
        Index index = new Index("test");

        Injector parentInjector = new ModulesBuilder()
                .add(new SettingsModule(EMPTY_SETTINGS), 
                     new EnvironmentModule(new Environment(EMPTY_SETTINGS)), 
                     new IndicesAnalysisModule())
                .createInjector();
        
        Injector injector = new ModulesBuilder().add(
                                new IndexSettingsModule(index, EMPTY_SETTINGS),
                                new IndexNameModule(index),
                                new AnalysisModule(EMPTY_SETTINGS, parentInjector.getInstance(IndicesAnalysisService.class))
                                    .addProcessor(new ChineseWordAnalysisBinderProcessor()))
                            .createChildInjector(parentInjector);

        AnalysisService analysisService = injector.getInstance(AnalysisService.class);

        TokenizerFactory tokenizerFactory = analysisService.tokenizer("word");
        boolean match = (tokenizerFactory instanceof ChineseWordTokenizerFactory);
        assertTrue(match);
        
        Tokenizer tokenizer = tokenizerFactory.create(new StringReader("他说的确实在理"));
        String exp = "[确实, 在理]";
        List<String> result = new ArrayList<>();
        while(tokenizer.incrementToken()){
            CharTermAttribute charTermAttribute = tokenizer.getAttribute(CharTermAttribute.class);
            result.add(charTermAttribute.toString());
        }
        assertEquals(exp, result.toString());

        Analyzer analyzer = analysisService.analyzer("word").analyzer();
        match = (analyzer instanceof ChineseWordAnalyzer);
        assertTrue(match);
        
        TokenStream tokenStream = analyzer.tokenStream("text", "杨尚川是APDPlat应用级产品开发平台的作者");
        exp = "[杨尚川, apdplat, 应用级, 产品, 开发平台, 作者]";
        result = new ArrayList<>();
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            result.add(charTermAttribute.toString());
        }
        assertEquals(exp, result.toString());
    }
}