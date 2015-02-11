package com.sitech.word.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sitech.word.SitechParticiple;
import com.sitech.word.core.segmentation.SegmentationAlgorithm;

public class TestWordUtil {
//	private static char[] chars = {'��',',','��','��','?'};
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
                    //�����ı�����
                    continue outer;
                }
            }
//            if(is(c)){
//                if(i > start){
//                    list.add(text.substring(start, i));
//                    //��һ�俪ʼ����
//                    start = i+1;
//                }else{
//                    //����������
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
		SitechParticiple sitech = SitechParticiple.create(SegmentationAlgorithm.FullSegmentation).addText("����,�ҵ��й���������ô��ΰ��,������������ˮ������֮�С�");
		sitech.addText("���������ʲô��");
		sitech.start();
//		List<String> str = TestWordUtil.seg("����,�ҵ��й���������ô��ΰ��������������ˮ������֮�С�", false, '��','��');
//		System.out.println(str);
//		char[] a = {',','.','?','{','}','��',' ',' '};
//		int x= Arrays.binarySearch(a, ' ');
//		System.out.println(x);
	}

}
