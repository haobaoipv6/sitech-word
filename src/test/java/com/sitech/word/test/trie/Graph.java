package com.sitech.word.test.trie;

public class Graph {
	//定点数组
	private Vertex vertexList;
	//临界矩阵
	private int [][] adjMat;
	
	//定点的最大数目
	private int maxSize;
	
	//当前节点
	private int nVertex;

	public Graph() {
		vertexList =  new Vertex();
		adjMat = new int[maxSize][maxSize];
		for(int x =0;x<maxSize;x++){
			for(int y=0;y<maxSize;y++){
				adjMat[x][y]=0;
			}
		}
		nVertex = 0;
	}
	
	public void addVertex(char lable){
		
	}
	
	
	
}
