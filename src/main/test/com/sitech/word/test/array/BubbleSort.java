package com.sitech.word.test.array;

public class BubbleSort {
	
	public static long [] sort(long [] arr){
		long tmp = 0;
		for(int x=0;x<arr.length-1;x++){
			for(int y=arr.length-1;y>x;y--){
				if(arr[y]<arr[y-1]){
					tmp = arr[y];
					arr[y]=arr[y-1];
					arr[y-1] = tmp;
				}
			}
		}
		return arr;
	}
	
	public static void main(String[] args) {
		long [] nums = BubbleSort.sort(new long[]{10,9,15,-10,50,4});
		for(int x=0;x<nums.length;x++){
			System.out.println(nums[x]);
		}
	}

}
