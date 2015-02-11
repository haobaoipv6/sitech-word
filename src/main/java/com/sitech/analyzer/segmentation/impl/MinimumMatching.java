package com.sitech.analyzer.segmentation.impl;

import java.util.ArrayList;
import java.util.List;

import com.sitech.analyzer.segmentation.Word;
import com.sitech.analyzer.util.RecognitionTool;

/**
 * 基于词典的正向最小匹配算法
 * Dictionary-based minimum matching algorithm
 * @author hb
 */
public class MinimumMatching extends AbstractSegmentation{
    @Override
    public List<Word> segImpl(String text) {
        List<Word> result = new ArrayList<>();
        //文本长度
        final int textLen=text.length();
        //从未分词的文本中截取的长度
        int len=1;
        //剩下未分词的文本的索引
        int start=0;
        //只要有词未切分完就一直继续
        while(start<textLen){
            //用长为len的字符串查词典，并做特殊情况识别
            while(!DIC.contains(text, start, len) && !RecognitionTool.recog(text, start, len)){
                //如果长度为词典最大长度且在词典中未找到匹配
                //或已经遍历完剩下的文本且在词典中未找到匹配
                //则按长度为一切分
                if(len==getInterceptLength() || len==textLen-start){
                    //重置截取长度为一
                    len=1;
                    break;
                }
                //如果查不到，则长度加一后继续
                len++;
            }
            addWord(result, text, start, len);
            //从待分词文本中向后移动索引，滑过已经分词的文本
            start+=len;
            //每一次成功切词后都要重置截取长度
            len=1;
        }
        return result;
    }
    public static void main(String[] args){
        String text = "《红楼梦》的作者是曹雪芹。课文里有一篇鲁迅的《从百草园到三味书屋》。他的文章在《人民日报》上发表了。桌上放着一本《中国语文》。《〈中国工人〉发刊词》发表于1940年2月7日。杨尚川是APDPlat应用级产品开发平台的作者";
        if(args !=null && args.length == 1){
            text = args[0];
        }
        MinimumMatching m = new MinimumMatching();
        LOGGER.info(m.seg(text).toString());
    }
}
