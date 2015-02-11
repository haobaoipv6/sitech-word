package com.sitech.analyzer.segmentation;

import java.util.List;

/**
 * 分词接口
 * Word Segmentation Interface
 * @author hb
 */
public interface Segmentation {
    public List<Word> seg(String text);
}
