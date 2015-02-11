package com.sitech.word.dic.impl;

import java.util.Arrays;
import java.util.Collection;

public class TrieNode implements Comparable<Object>{
	private char data;
	private boolean terminal;
	private TrieNode rootNode;
	private TrieNode[] children = new TrieNode[0];
	private int size;

	public TrieNode(char data) {
		this.data = data;
	}

	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public char getCharacter() {
		return data;
	}

	public void setCharacter(char data) {
		this.data = data;
	}

	public TrieNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TrieNode rootNode) {
		this.rootNode = rootNode;
	}

	public Collection<TrieNode> getChildren() {
		return Arrays.asList(children);
	}

	/**
	 * 利用二分搜索算法从有序数组中找到特定的节点
	 * @param character 待查找节点
	 * @return NULL OR 节点数据
	 */
	public TrieNode getChild(char character) {
		int index = Arrays.binarySearch(children, character);
		if (index >= 0) {
			return children[index];
		}
		return null;
	}

	public TrieNode getChildIfNotExistThenCreate(char character) {
		TrieNode child = getChild(character);
		if (child == null) {
			child = new TrieNode(character);
			addChild(child);
		}
		return child;
	}

	public void addChild(TrieNode child) {
		children = insert(children, child);
	}

	/**
	 * 将一个字符追加到有序数组
	 * @param array 有序数组
	 * @param element 字符
	 * @return 新的有序数字
	 */
	private TrieNode[] insert(TrieNode[] array, TrieNode element) {
		int length = array.length;
		if (length == 0) {
			array = new TrieNode[1];
			array[0] = element;
			return array;
		}
		TrieNode[] newArray = new TrieNode[length + 1];
		boolean insert = false;
		for (int i = 0; i < length; i++) {
			if (element.getCharacter() <= array[i].getCharacter()) {
				// 新元素找到合适的插入位置
				newArray[i] = element;
				// 将array中剩下的元素依次加入newArray即可退出比较操作
				System.arraycopy(array, i, newArray, i + 1, length - i);
				insert = true;
				break;
			} else {
				newArray[i] = array[i];
			}
		}
		if (!insert) {
			// 将新元素追加到尾部
			newArray[length] = element;
		}
		return newArray;
	}

	public int compareTo(Object o) {
		return this.getCharacter() - (Character) o;
	}
	
	
	public static void main(String[] args) {
		TrieNode trieNode = new TrieNode('我');
		TrieNode[] children = {new TrieNode('是'),new TrieNode('你')};
		TrieNode element = new TrieNode('爸');
		TrieNode[] nodes = trieNode.insert(children, element);
		for(int x=0;x<nodes.length;x++){
			System.out.println(nodes[x].getCharacter());
		}
	}
}
