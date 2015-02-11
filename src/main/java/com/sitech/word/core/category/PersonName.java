package com.sitech.word.core.category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * 人名识别
 */
public class PersonName implements ICategory{
	private Set<String> personName_data = new HashSet<String>();
	
	@Override
	public Set<String> loadResource() {
		InputStream in = Punctuation.class.getClassLoader().getResourceAsStream("surname.txt");
    	try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line = null;
			while((line = reader.readLine()) != null){
				line = line.trim();
				if(line.contains("#")){
					continue;
				}
				personName_data.add(line);
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return personName_data;
	}
   
}