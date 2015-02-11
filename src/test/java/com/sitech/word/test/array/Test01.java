package com.sitech.word.test.array;

public class Test01 {
	
	public static void main(String[] args) {
		long [] a = new long[]{1,2,3,4,5};
		System.out.println(a[1]);
		
		String [] s = new String[10];
		for(int x =0;x<s.length;x++){
			s[x]=x+"";
		}
	}
}
