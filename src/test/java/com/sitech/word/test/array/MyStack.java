package com.sitech.word.test.array;

public class MyStack {
	private long [] arr ;
	private int top;
	
	public MyStack(){
		arr = new long[50];
		top = -1;
	}
	
	public MyStack(int size){
		arr = new long[size];
		top = -1;
	}
	
	public void push(int data){
		arr[++top] = data;
	}
	
	public long pop(){
		return arr[top--];
	}
	
	public long peek(){
		return arr[top];
	}
	
	public boolean isEmpty(){
		return top==-1?true:false;
	}

	public static void main(String[] args) {
		
	}

}
