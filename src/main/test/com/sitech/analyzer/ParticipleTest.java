package com.sitech.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.bean.Word;

public class ParticipleTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(Participle.class);

	public static void demo() {
		long start = System.currentTimeMillis();
		List<String> sentences = new ArrayList<>();
		sentences.add("他说的确实在理");
		sentences.add("提高人民生活水平");
		sentences.add("他俩儿谈恋爱是从头年元月开始的");
		sentences.add("王府饭店的设施和服务是一流的");
		sentences.add("和服务于三日后裁制完毕，并呈送将军府中");
		sentences.add("研究生命的起源");
		sentences.add("他明天起身去北京");
		sentences.add("在这些企业中国有企业有十个");
		sentences.add("他站起身来");
		sentences.add("他们是来查金泰撞人那件事的");
		sentences.add("行侠仗义的查金泰远近闻名");
		sentences.add("长春市长春节致辞");
		sentences.add("他从马上摔下来了,你马上下来一下");
		sentences.add("乒乓球拍卖完了");
		sentences.add("咬死猎人的狗");
		sentences.add("地面积了厚厚的雪");
		sentences.add("这几块地面积还真不小");
		sentences.add("大学生活象白纸");
		sentences.add("结合成分子式");
		sentences.add("有意见分歧");
		sentences.add("发展中国家兔的计划");
		sentences.add("明天他将来北京");
		sentences.add("税收制度将来会更完善");
		sentences.add("依靠群众才能做好工作");
		sentences.add("现在是施展才能的好机会");
		sentences.add("把手举起来");
		sentences.add("茶杯的把手断了");
		sentences.add("以新的姿态出现在世界东方");
		sentences.add("使节约粮食进一步形成风气");
		sentences.add("反映了一个人的精神面貌");
		sentences.add("美国加州大学的科学家发现");
		sentences.add("我好不挺好");
		sentences.add("木有");
		sentences.add("下雨天留客天天留我不留");
		sentences.add("叔叔亲了我妈妈也亲了我");
		sentences.add("白马非马");
		sentences.add("学生会写文章");
		sentences.add("张掖市民陈军");
		sentences.add("张掖市明乐县");
		sentences.add("中华人民共和国万岁万岁万万岁");
		sentences.add("江阴毛纺厂成立了保持党员先进性爱国主义学习小组,在江阴道路管理局协助下,通过宝鸡巴士公司,与蒙牛酸酸乳房山分销点组成了开放性交互式的讨论组, 认为google退出中国事件赤裸裸体现了帝国主义的文化侵略,掀起了爱国主义的群众性高潮。");
		int i = 1;
		for (String sentence : sentences) {
			List<Word> words = Participle.segWithStopWords(sentence);
			LOGGER.info((i++) + "、切分句子: " + sentence);
			LOGGER.info("    切分结果：" + words);
		}
		long cost = System.currentTimeMillis() - start;
		LOGGER.info("耗时: " + cost + " 毫秒");
	}

	public static void main(String[] args) {
//		List<Word> s = Participle.segWithStopWords("他起身关上电脑，乐享3G69如何呢？用滚烫的开水为自己泡制一碗腾着热气的老坛酸菜面 ");
//		System.out.println(s);
		demo();
		//6!/2*4!=3*5
	}

}
