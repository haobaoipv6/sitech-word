package com.sitech.analyzer.algorithm.participle.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sitech.analyzer.algorithm.participle.IParticiple;
import com.sitech.analyzer.algorithm.participle.impl.MaximumMatching;
import com.sitech.analyzer.bean.Word;

import static org.junit.Assert.*;

/**
 *
 * @author hb
 */
public class MaximumMatchingTest {
    @Test
    public void testSeg() {
        IParticiple segmentation = new MaximumMatching();
        List<String> text = new ArrayList<>();
        text.add("长春市长春节致辞");
        text.add("他说的确实在理");
        text.add("提高人民生活水平");
        text.add("他俩儿谈恋爱是从头年元月开始的");
        text.add("王府饭店的设施和服务是一流的");
        text.add("和服务于三日后裁制完毕，并呈送将军府中");
        text.add("研究生命的起源");
        text.add("他明天起身去北京");
        text.add("在这些企业中国有企业有十个");
        text.add("他站起身来");
        text.add("他们是来查金泰撞人那件事的");
        text.add("行侠仗义的查金泰远近闻名");
        text.add("他从马上摔下来了,你马上下来一下");
        text.add("乒乓球拍卖完了");
        text.add("咬死猎人的狗");
        text.add("地面积了厚厚的雪");
        text.add("这几块地面积还真不小");
        text.add("大学生活象白纸");
        text.add("结合成分子式");
        text.add("有意见分歧");
        text.add("发展中国家兔的计划");
        text.add("明天他将来北京");
        text.add("税收制度将来会更完善");
        text.add("依靠群众才能做好工作");
        text.add("现在是施展才能的好机会");
        text.add("把手举起来");
        text.add("茶杯的把手断了");
        text.add("以新的姿态出现在世界东方");
        text.add("使节约粮食进一步形成风气");
        text.add("反映了一个人的精神面貌");
        text.add("美国加州大学的科学家发现");
        text.add("我好不挺好");
        text.add("木有");
        text.add("下雨天留客天天留我不留");
        text.add("叔叔亲了我妈妈也亲了我");
        text.add("我爱北京天安门，我来自河北，你呢？");
        text.add("几经查找,终于找到其issue系统的地址,是个bugzilla呢 – 天啊,官网一个网站,下载一个网站,源码管理一个网站,issue管理又是再一个网站");
        
//        List<String> expResult = new ArrayList<>();
//        expResult.add("[长春市, 长春, 节, 致辞]");
//        expResult.add("[杨]");
//        expResult.add("[他, 说, 的确, 实在, 理]");
//        expResult.add("[提高, 人民, 生活, 水平]");
//        expResult.add("[他俩, 儿, 谈恋爱, 是从, 头年, 元月, 开始, 的]");
//        expResult.add("[王府, 饭店, 的, 设施, 和服, 务, 是, 一流, 的]");
//        expResult.add("[和服, 务, 于, 三日, 后, 裁制, 完毕, 并, 呈送, 将军府, 中]");
//        expResult.add("[研究生, 命, 的, 起源]");
//        expResult.add("[他, 明天, 起身, 去, 北京]");
//        expResult.add("[在, 这些, 企业, 中国, 有, 企业, 有, 十个]");
//        expResult.add("[他, 站起, 身, 来]");
//        expResult.add("[他们, 是, 来, 查, 金泰, 撞人, 那件事, 的]");
//        expResult.add("[行侠仗义, 的, 查, 金泰, 远近闻名]");
//        expResult.add("[他, 从, 马上, 摔下来, 了, 你, 马上, 下来, 一下]");
//        expResult.add("[乒乓球拍, 卖完, 了]");
//        expResult.add("[咬死, 猎人, 的, 狗]");
//        expResult.add("[地面, 积, 了, 厚厚的, 雪]");
//        expResult.add("[这, 几, 块, 地面, 积, 还真, 不小]");
//        expResult.add("[大学生, 活象, 白纸]");
//        expResult.add("[结合, 成分, 子式]");
//        expResult.add("[有意见, 分歧]");
//        expResult.add("[发展中, 国家, 兔, 的, 计划]");
//        expResult.add("[明天, 他, 将来, 北京]");
//        expResult.add("[税收, 制度, 将来, 会, 更, 完善]");
//        expResult.add("[依靠, 群众, 才能, 做好, 工作]");
//        expResult.add("[现在, 是, 施展, 才能, 的, 好机会]");
//        expResult.add("[把手, 举起来]");
//        expResult.add("[茶杯, 的, 把手, 断了]");
//        expResult.add("[以, 新的, 姿态, 出现在, 世界, 东方]");
//        expResult.add("[使节, 约, 粮食, 进一步, 形成, 风气]");
//        expResult.add("[反映, 了, 一个人, 的, 精神, 面貌]");
//        expResult.add("[美国, 加州, 大学, 的, 科学家, 发现]");
//        expResult.add("[我, 好不, 挺好]");
//        expResult.add("[木, 有]");
//        expResult.add("[下雨天, 留客, 天天, 留, 我, 不留]");
//        expResult.add("[叔叔, 亲了, 我, 妈妈, 也, 亲了, 我]");
        
        for(int i=0; i<text.size(); i++){
            List<Word> result = segmentation.seg(text.get(i));
//            assertEquals(expResult.get(i).toString(), result.toString());
            System.out.println(result);
        }
    }
    
    
    public static void main(String[] args) {
    	MaximumMatchingTest test = new MaximumMatchingTest();
    	test.testSeg();
	}
}
