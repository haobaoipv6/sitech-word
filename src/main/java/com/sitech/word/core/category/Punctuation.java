package com.sitech.word.core.category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.sitech.word.SitechParticiple;

public class Punctuation implements ICategory{
	private Set<String> punctation_data = new HashSet<String>();
	
	@Override
	public Set<String> loadResource() {
		InputStream in = Punctuation.class.getClassLoader().getResourceAsStream("punctuation.txt");
    	try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line = null;
			int x =0;
			while((line = reader.readLine()) != null){
				line = line.trim();
				if(line.contains("#")){
					continue;
				}
				char[] a = line.toCharArray();
				for(char l:a){
					SitechParticiple.chars[x] = l;
				}
				x++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return punctation_data;
	}
	
	public static void main(String[] args) {
		Punctuation category = new Punctuation();
		category.loadResource();
	}

}
