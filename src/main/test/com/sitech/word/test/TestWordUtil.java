package com.sitech.word.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sitech.word.SitechParticiple;
import com.sitech.word.core.segmentation.SegmentationAlgorithm;

public class TestWordUtil {
//	private static char[] chars = {'，',',','。','？','?'};
//	
//	public static boolean is(char _char){
//        int index = Arrays.binarySearch(chars, _char);
//        return index >= 0;
//    }

	public static List<String> seg(String text, boolean withPunctuation, char... reserve){
        List<String> list = new ArrayList<String>();
        int start = 0;
        char[] array = text.toCharArray();
        int len = array.length;
        outer:for(int i=0; i<len; i++){
            char c = array[i];
            for(char t : reserve){
                if(c == t){
                    //保留的标点符号
                    continue outer;
                }
            }
//            if(is(c)){
//                if(i > start){
//                    list.add(text.substring(start, i));
//                    //下一句开始索引
//                    start = i+1;
//                }else{
//                    //跳过标点符号
//                    start++;
//                }
//                if(withPunctuation){
//                    list.add(Character.toString(c));
//                }
//            }
        }
        if(len - start > 0){
            list.add(text.substring(start, len));
        }
        return list;
    }
	public static void main(String[] args) {
		SitechParticiple sitech = SitechParticiple.create(SegmentationAlgorithm.FullSegmentation).addText("北京,我的中国。你是那么的伟大,让人民生活在水生火热之中。");
		sitech.addText("你他妈的是什么？");
		sitech.start();
//		List<String> str = TestWordUtil.seg("北京,我的中国。你是那么的伟大？让人民生活在水生火热之中。", false, '。','。');
//		System.out.println(str);
//		char[] a = {',','.','?','{','}','。',' ',' '};
//		int x= Arrays.binarySearch(a, ' ');
//		System.out.println(x);
	}

}
