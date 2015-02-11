package com.sitech.analyzer.dictionary;

import java.util.List;

/**
 * 词典
 * @author hb
 */
public interface Dictionary {
    public int getMaxLength();
    public boolean contains(String item, int start, int length);
    public boolean contains(String item);
    public void addAll(List<String> items);
    public void add(String item);
    public void removeAll(List<String> items);
    public void remove(String item);
    public void clear();
}
