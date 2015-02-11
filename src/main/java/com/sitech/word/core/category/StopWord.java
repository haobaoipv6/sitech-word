package com.sitech.word.core.category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class StopWord implements ICategory{
	private Set<String> stopWord_data = new HashSet<String>();

	@Override
	public Set<String> loadResource() {
		InputStream in = Punctuation.class.getClassLoader().getResourceAsStream("stopwords.txt");
    	try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line = null;
			while((line = reader.readLine()) != null){
				line = line.trim();
				if(line.contains("#")){
					continue;
				}
				stopWord_data.add(line);
				System.out.println(line.charAt(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return stopWord_data;
	}
   
}