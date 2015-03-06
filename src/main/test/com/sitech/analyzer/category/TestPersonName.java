package com.sitech.analyzer.category;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.bean.Word;
import com.sitech.analyzer.categroy.PersonName;

/**
 * test人名识别
 * @author hb
 */
public class TestPersonName {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPersonName.class);

	public static void main(String[] args){
        int i=1;
        for(String str : PersonName.getSurname1()){
            LOGGER.info((i++)+" : "+str);
        }
        for(String str : PersonName.getSurname2()){
            LOGGER.info((i++)+" : "+str);
        }
        LOGGER.info("张三："+PersonName.is("张三"));
        LOGGER.info("欧阳飞燕："+PersonName.is("欧阳飞燕"));
        LOGGER.info("令狐冲："+PersonName.is("令狐冲"));
        LOGGER.info("张三爱读书："+PersonName.is("张三爱读书"));
        List<Word> test = new ArrayList<>();
        test.add(new Word("快"));
        test.add(new Word("来"));
        test.add(new Word("看"));
        test.add(new Word("张"));
        test.add(new Word("三"));
        test.add(new Word("表演"));
        test.add(new Word("魔术"));
        test.add(new Word("了"));
        LOGGER.info(PersonName.recognize(test).toString());
        
        test = new ArrayList<>();
        test.add(new Word("李"));
        test.add(new Word("世"));
        test.add(new Word("明"));
        test.add(new Word("的"));
        test.add(new Word("昭仪"));
        test.add(new Word("欧阳"));
        test.add(new Word("飞"));
        test.add(new Word("燕"));
        test.add(new Word("其实"));
        test.add(new Word("很"));
        test.add(new Word("厉害"));
        test.add(new Word("呀"));
        test.add(new Word("！"));
        test.add(new Word("比"));
        test.add(new Word("公孙"));
        test.add(new Word("黄"));
        test.add(new Word("后"));
        test.add(new Word("牛"));
        LOGGER.info(PersonName.recognize(test).toString());
                
        test = new ArrayList<>();
        test.add(new Word("发展"));
        test.add(new Word("中国"));
        test.add(new Word("家兔"));
        test.add(new Word("的"));
        test.add(new Word("计划"));
        LOGGER.info(PersonName.recognize(test).toString());
        
        test = new ArrayList<>();
        test.add(new Word("张三"));
        test.add(new Word("好"));
        LOGGER.info(PersonName.recognize(test).toString());
        
        LOGGER.info(PersonName.getSurname("欧阳锋"));
        LOGGER.info(PersonName.getSurname("李阳锋"));
    }

}
