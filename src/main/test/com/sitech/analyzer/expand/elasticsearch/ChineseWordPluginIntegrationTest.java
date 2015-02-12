package com.sitech.analyzer.expand.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;

/**
 * ElasticSearch中文分词插件集成测试
 * @author hb
 */
public class ChineseWordPluginIntegrationTest extends ElasticsearchIntegrationTest {
    @Test
    public void testChineseWordAnalyzer() throws ExecutionException, InterruptedException {
        AnalyzeResponse response = client().admin().indices()
                .prepareAnalyze("杨尚川是APDPlat应用级产品开发平台的作者").setAnalyzer("word")
                .execute().get();

        assertThat(response, notNullValue());
        assertThat(response.getTokens().size(), is(6));
        
        String exp = "[杨尚川, apdplat, 应用级, 产品, 开发平台, 作者]";
        List<String> result = new ArrayList<>();
        for(AnalyzeResponse.AnalyzeToken token : response.getTokens()){
            result.add(token.getTerm());
        }
        assertThat(result.toString(), equalTo(exp));
    }
    @Test
    public void testChineseWordTokenizer() throws ExecutionException, InterruptedException {
        AnalyzeResponse response = client().admin().indices()
                .prepareAnalyze("他说的确实在理").setTokenizer("word")
                .execute().get();

        assertThat(response, notNullValue());
        assertThat(response.getTokens().size(), is(2));
        
        String exp = "[确实, 在理]";
        List<String> result = new ArrayList<>();
        for(AnalyzeResponse.AnalyzeToken token : response.getTokens()){
            result.add(token.getTerm());
        }
        assertThat(result.toString(), equalTo(exp));
    }
}