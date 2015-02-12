package com.sitech.analyzer.categroy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.util.AutoDetector;
import com.sitech.analyzer.util.IResource;
import com.sitech.analyzer.util.WordConfUtil;

/**
 * 数量词识别
 * @author hb
 */
public class Quantifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(Quantifier.class);
    private static final Set<Character> quantifiers=new HashSet<>();
    static{
        reload();
    }
    public static void reload(){
        AutoDetector.loadAndWatch(new IResource(){

            @Override
            public void clear() {
                quantifiers.clear();
            }

            @Override
            public void load(List<String> lines) {
                LOGGER.info("初始化数量词");
                for(String line : lines){
                    if(line.length() == 1){
                        char _char = line.charAt(0);
                        if(quantifiers.contains(_char)){
                            LOGGER.info("配置文件有重复项："+line);
                        }else{
                            quantifiers.add(_char);
                        }
                    }else{
                        LOGGER.info("忽略不合法数量词："+line);
                    }
                }
                LOGGER.info("数量词初始化完毕，数量词个数："+quantifiers.size());
            }

            @Override
            public void add(String line) {
                if (line.length() == 1) {
                    char _char = line.charAt(0);
                    quantifiers.add(_char);
                } else {
                    LOGGER.info("忽略不合法数量词：" + line);
                }
            }

            @Override
            public void remove(String line) {
                if (line.length() == 1) {
                    char _char = line.charAt(0);
                    quantifiers.remove(_char);
                } else {
                    LOGGER.info("忽略不合法数量词：" + line);
                }
            }
        
        }, WordConfUtil.get("quantifier.path", "classpath:quantifier.txt"));
    }
    public static boolean is(char _char){
        return quantifiers.contains(_char);
    }
    public static void main(String[] args){
        int i=1;
        for(char quantifier : quantifiers){
            LOGGER.info((i++)+" : "+quantifier);
        }
    }
}
