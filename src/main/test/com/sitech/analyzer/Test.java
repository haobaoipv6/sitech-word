package com.sitech.analyzer;

import java.util.List;

import com.sitech.analyzer.bean.Word;

public class Test {

	public static void main(String[] args) {
		List<Word> s = Participle.segWithStopWords("他起身关上电脑，乐享3G69如何呢？用滚烫的开水为自己泡制一碗腾着热气的老坛酸菜面 ");
		System.out.println(s);
//		System.out.println(Participle.segWithStopWords("中华人民共和国，我爱北京天安门。"));
	}

}
