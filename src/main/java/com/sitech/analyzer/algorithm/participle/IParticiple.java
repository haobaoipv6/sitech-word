package com.sitech.analyzer.algorithm.participle;

import java.util.List;

import com.sitech.analyzer.bean.Word;

/**
 * 分词接口
 * Word Segmentation Interface
 * @author hb
 */
public interface IParticiple {
    public List<Word> seg(String text);
}
