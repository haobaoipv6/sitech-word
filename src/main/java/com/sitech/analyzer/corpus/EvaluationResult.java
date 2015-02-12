package com.sitech.analyzer.corpus;

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;

/**
 * 中文分词效果评估结果
 * @author hb
 */
public class EvaluationResult implements Comparable{
    private SegmentationAlgorithm segmentationAlgorithm;
    private float segSpeed;
    private int totalLineCount;
    private int perfectLineCount;
    private int wrongLineCount;
    private int totalCharCount;
    private int perfectCharCount;
    private int wrongCharCount;

    public SegmentationAlgorithm getSegmentationAlgorithm() {
        return segmentationAlgorithm;
    }
    public void setSegmentationAlgorithm(SegmentationAlgorithm segmentationAlgorithm) {
        this.segmentationAlgorithm = segmentationAlgorithm;
    }
    public float getSegSpeed() {
        return segSpeed;
    }
    public void setSegSpeed(float segSpeed) {
        this.segSpeed = segSpeed;
    }
    public float getLinePerfectRate(){
        return perfectLineCount/(float)totalLineCount*100;
    }
    public float getLineWrongRate(){
        return wrongLineCount/(float)totalLineCount*100;
    }
    public float getCharPerfectRate(){
        return perfectCharCount/(float)totalCharCount*100;
    }
    public float getCharWrongRate(){
        return wrongCharCount/(float)totalCharCount*100;
    }
    public int getTotalLineCount() {
        return totalLineCount;
    }
    public void setTotalLineCount(int totalLineCount) {
        this.totalLineCount = totalLineCount;
    }
    public int getPerfectLineCount() {
        return perfectLineCount;
    }
    public void setPerfectLineCount(int perfectLineCount) {
        this.perfectLineCount = perfectLineCount;
    }
    public int getWrongLineCount() {
        return wrongLineCount;
    }
    public void setWrongLineCount(int wrongLineCount) {
        this.wrongLineCount = wrongLineCount;
    }
    public int getTotalCharCount() {
        return totalCharCount;
    }
    public void setTotalCharCount(int totalCharCount) {
        this.totalCharCount = totalCharCount;
    }
    public int getPerfectCharCount() {
        return perfectCharCount;
    }
    public void setPerfectCharCount(int perfectCharCount) {
        this.perfectCharCount = perfectCharCount;
    }
    public int getWrongCharCount() {
        return wrongCharCount;
    }
    public void setWrongCharCount(int wrongCharCount) {
        this.wrongCharCount = wrongCharCount;
    }
    @Override
    public String toString(){
        return segmentationAlgorithm.name()+"（"+segmentationAlgorithm.getDes()+"）："
                +"\n"
                +"分词速度："+segSpeed+" 字符/毫秒"
                +"\n"
                +"行数完美率："+getLinePerfectRate()+"%"
                +"  行数错误率："+getLineWrongRate()+"%"
                +"  总的行数："+totalLineCount
                +"  完美行数："+perfectLineCount
                +"  错误行数："+wrongLineCount
                +"\n"
                +"字数完美率："+getCharPerfectRate()+"%"
                +" 字数错误率："+getCharWrongRate()+"%"
                +" 总的字数："+totalCharCount
                +" 完美字数："+perfectCharCount
                +" 错误字数："+wrongCharCount;
    }
    @Override
    public int compareTo(Object o) {
        EvaluationResult other = (EvaluationResult)o;
        if(other.getLinePerfectRate() - getLinePerfectRate() > 0){
            return 1;
        }
        if(other.getLinePerfectRate() - getLinePerfectRate() < 0){
            return -1;
        }
        return 0;
    }
}