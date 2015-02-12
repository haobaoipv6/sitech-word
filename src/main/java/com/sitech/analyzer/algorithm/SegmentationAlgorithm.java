package com.sitech.analyzer.algorithm;

/**
 * 中文分词算法
 * Chinese word segmentation algorithm
 * @author hb
 */
public enum SegmentationAlgorithm {
    /**
     * 正向最大匹配算法
     */
    MaximumMatching("正向最大匹配算法"),
    /**
     * 逆向最大匹配算法
     */
    ReverseMaximumMatching("逆向最大匹配算法"),
    /**
     * 正向最小匹配算法
     */
    MinimumMatching("正向最小匹配算法"),
    /**
     * 逆向最小匹配算法
     */
    ReverseMinimumMatching("逆向最小匹配算法"),
    /**
     * 双向最大匹配算法
     */
    BidirectionalMaximumMatching("双向最大匹配算法"),
    /**
     * 双向最小匹配算法
     */
    BidirectionalMinimumMatching("双向最小匹配算法"),
    /**
     * 双向最大最小匹配算法
     */
    BidirectionalMaximumMinimumMatching("双向最大最小匹配算法"),
    /**
     * 全切分算法
     */
    FullSegmentation("全切分算法");
    private SegmentationAlgorithm(String des){
        this.des = des;
    }
    private final String des;
    public String getDes() {
        return des;
    }
}
