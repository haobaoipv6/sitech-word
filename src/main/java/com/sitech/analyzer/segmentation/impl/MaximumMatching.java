package com.sitech.analyzer.segmentation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sitech.analyzer.segmentation.Word;
import com.sitech.analyzer.util.RecognitionTool;

/**
 * 基于词典的正向最大匹配算法
 * Dictionary-based maximum matching algorithm
 * @author hb
 */
public class MaximumMatching extends AbstractSegmentation{
    @Override
    public List<Word> segImpl(String text) {
        List<Word> result = new ArrayList<>();
        //文本长度
        final int textLen=text.length();
        //从未分词的文本中截取的长度
        int len=getInterceptLength();
        //剩下未分词的文本的索引
        int start=0;
        //只要有词未切分完就一直继续
        while(start<textLen){
            if(len>textLen-start){
                //如果未分词的文本的长度小于截取的长度
                //则缩短截取的长度
                len=textLen-start;
            }
            //用长为len的字符串查词典，并做特殊情况识别
            while(!DIC.contains(text, start, len) && !RecognitionTool.recog(text, start, len)){
                //如果长度为一且在词典中未找到匹配
                //则按长度为一切分
                if(len==1){
                    break;
                }
                //如果查不到，则长度减一后继续
                len--;
            }
            addWord(result, text, start, len);
            //从待分词文本中向后移动索引，滑过已经分词的文本
            start+=len;
            //每一次成功切词后都要重置截取长度
            len=getInterceptLength();
        }
        return result;
    }    
    public static void main(String[] args){
        String text = "他十分惊讶地说：“啊，原来是您，杨尚川！能见到您真是太好了，我有个Nutch问题想向您请教呢！”";
        if(args !=null && args.length == 1){
            text = args[0];
        }
        MaximumMatching m = new MaximumMatching();
        LOGGER.info(m.seg(text).toString());
    }
}
