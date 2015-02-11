package com.sitech.word.test.array;

public class MyArray {
	private long[] arr;
	
	//有效数据的长度
	private int arraySize;
	
	public MyArray(){
		arr = new long[30];
	}
	
	public MyArray(int size){
		arr = new long[size];
	}
	
	public void add(long l){
//		arr[arraySize] = l;
//		arraySize++;
		int i;
		for(i=0;i<arraySize;i++){
			if(arr[i]>l){
				break;
			}
		}
		
		for(int x=arraySize;x>i;x--){
			arr[x] = arr[x-1];
		}
		arr[i] =l;
		arraySize++;
		
	}
	
	public int searchByIndex(int value){
		for(int x=0;x<arraySize;x++){
			if(arr[x]==value){
				return x;
			}
		}
		return -1;
	}
	
	public void display(){
		System.out.print("[");
		for(int x =0;x<arraySize;x++){
			System.out.print(arr[x] +" ");
		}
		System.out.print("]");
	}
	
	public void del(int index) throws Exception{
		if(index<0 && index>=arraySize){
			throw new Exception();
		}
		for(int x=0;x<arraySize;x++){
			arr[index] = arr[index+1];
		}
		arraySize--;
	}
	
	public void update(int index,long value) throws Exception{
		if(index<0 && index>=arraySize){
			throw new Exception();
		}
		arr[index] =value;
	}
	
	public static void main(String[] args) throws Exception {
		MyArray my = new MyArray();
		my.add(12);
		my.add(11);
		my.add(10);
		my.display();
		
		int y = my.searchByIndex(10);
		System.out.println(y);
		
//		my.update(1, 100);
		my.add(2);
		
		my.display();
	}
}
