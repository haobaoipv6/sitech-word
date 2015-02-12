package com.sitech.analyzer.recognition;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sitech.analyzer.categroy.Punctuation;

/**
 *
 * @author hb
 */
public class PunctuationTest {
    private static final List<Character> LIST = new ArrayList<>();
    @BeforeClass
    public static void initData() throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/punctuation-test.txt"), Charset.forName("utf-8"));
        for(String line : lines){
            LIST.add(line.trim().charAt(0));
        }
    }
    @Test
    public void testIs() {
        for(char item : LIST){
            boolean result = Punctuation.is(item);
            assertEquals(true, result);
        }
        assertEquals(false, Punctuation.is('y'));
        assertEquals(false, Punctuation.is('s'));
        assertEquals(false, Punctuation.is('1'));
    }
    @Test
    public void testHas() {
        assertEquals(true, Punctuation.has("杨尚川,系统架构设计师,系统分析师,2013年度优秀开源项目APDPlat发起人,资深Nutch搜索引擎专家。"));
        assertEquals(false, Punctuation.has("2.35"));
        assertEquals(false, Punctuation.has("80%"));
        assertEquals(false, Punctuation.has("情况会逐步得到缓解"));
        assertEquals(true, Punctuation.has("情况会逐步得到缓解。"));
        assertEquals(true, Punctuation.has("12月31日，中共中央总书记、国家主席江泽民发表1998年新年讲话《迈向充满希望的新世纪》。"));
    }
    @Test
    public void testSeg() {
        String text = "杨尚川,系统架构设计师,系统分析师,2013年度优秀开源项目APDPlat发起人,资深Nutch搜索引擎专家。";
        List<String> expResult = new ArrayList<>();
        expResult.add("杨尚川");
        expResult.add(",");
        expResult.add("系统架构设计师");
        expResult.add(",");
        expResult.add("系统分析师");
        expResult.add(",");
        expResult.add("2013年度优秀开源项目APDPlat发起人");
        expResult.add(",");
        expResult.add("资深Nutch搜索引擎专家");
        expResult.add("。");
        List<String> result = Punctuation.seg(text, true);
        assertEquals(expResult.toString(), result.toString());
        
        expResult = new ArrayList<>();
        expResult.add("杨尚川");
        expResult.add("系统架构设计师");
        expResult.add("系统分析师");
        expResult.add("2013年度优秀开源项目APDPlat发起人");
        expResult.add("资深Nutch搜索引擎专家");
        result = Punctuation.seg(text, false);
        assertEquals(expResult.toString(), result.toString());
        
        text = "杨尚川";
        assertEquals(text, Punctuation.seg(text, true).get(0));
        assertEquals(text, Punctuation.seg(text, false).get(0));
        
        text = "杨";
        assertEquals(text, Punctuation.seg(text, true).get(0));
        assertEquals(text, Punctuation.seg(text, false).get(0));
        
        text = "杨 ";
        assertEquals(text, Punctuation.seg(text, true).get(0)+Punctuation.seg(text, true).get(1));
        assertEquals(text, Punctuation.seg(text, false).get(0)+" ");
    }
}