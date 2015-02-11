package com.sitech.word.core.category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sitech.word.dic.IDic;
import com.sitech.word.dic.impl.DicImpl;

public class Dic implements ICategory {

	@Override
	public Set<String> loadResource() {
		 List<String> dic_data = new ArrayList<String>();
		 IDic dic = new DicImpl();
		InputStream in = Punctuation.class.getClassLoader().getResourceAsStream("dic.txt");
    	try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String line = null;
			while((line = reader.readLine()) != null){
				line = line.trim();
				if(line.contains("#")){
					continue;
				}
				dic_data.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	dic.addAll(dic_data);
//    	return dic_data;
    	return null;
	}

}
