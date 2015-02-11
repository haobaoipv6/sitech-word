package com.sitech.analyzer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 工具类
 * @author hb
 */
public class Utils {
    //至少出现一次中文字符，且以中文字符开头和结束
    private static final Pattern PATTERN_ONE = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
    //至少出现两次中文字符，且以中文字符开头和结束
    private static final Pattern PATTERN_TWO = Pattern.compile("^[\\u4e00-\\u9fa5]{2,}$");
    /**
     * 至少出现一次中文字符，且以中文字符开头和结束
     * @param word
     * @return 
     */
    public static boolean isChineseCharAndLengthAtLeastOne(String word){
        if(PATTERN_ONE.matcher(word).find()){
            return true;
        }
        return false;
    }
    /**
     * 至少出现两次中文字符，且以中文字符开头和结束
     * @param word
     * @return 
     */
    public static boolean isChineseCharAndLengthAtLeastTwo(String word){
        if(PATTERN_TWO.matcher(word).find()){
            return true;
        }
        return false;
    }
    /**
     * 删除目录
     * @param dir 目录
     * @return 是否成功
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children) {
                boolean success = deleteDir(child);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    /**
     * 根据MAP的VALUE进行排序
     * @param <K> key
     * @param <V> value
     * @param map map
     * @return 根据MAP的VALUE由大到小的排序结果列表
     */
    public static <K, V extends Number> List<Map.Entry<K, V>> getSortedMapByValue(Map<K, V> map) {        
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<K,V>>() {    
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {    
                if(o1.getValue() instanceof Integer){
                    return o2.getValue().intValue() - o1.getValue().intValue();
                }
                if(o1.getValue() instanceof Long){
                    return (int)(o2.getValue().longValue() - o1.getValue().longValue());
                }
                if(o1.getValue() instanceof Float){
                    float f1 = o1.getValue().floatValue();
                    float f2 = o2.getValue().floatValue();
                    if(f1 < f2){
                        return 1;
                    }
                    if(f1 == f2){
                        return 0;
                    }
                    return -1;
                }
                if(o1.getValue() instanceof Double){
                    double f1 = o1.getValue().doubleValue();
                    double f2 = o2.getValue().doubleValue();
                    if(f1 < f2){
                        return 1;
                    }
                    if(f1 == f2){
                        return 0;
                    }
                    return -1;
                }
                return 0;
            }    
        });     
        return list;  
    }
}
