package com.sitech.analyzer.algorithm.participle;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitech.analyzer.algorithm.SegmentationAlgorithm;

/**
 * 中文分词工厂类
 * 根据指定的分词算法返回分词实现
 * @author hb
 */
public class ParticipleFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipleFactory.class);
    private static final Map<String, IParticiple> pool = new HashMap<>();
    private ParticipleFactory(){};
    
    public static synchronized IParticiple getSegmentation(SegmentationAlgorithm segmentationAlgorithm){
        String clazz = "com.sitech.analyzer.algorithm.participle.impl."+segmentationAlgorithm.name();
        IParticiple segmentation = pool.get(clazz);
        if(segmentation == null){
            LOGGER.info("构造分词实现类："+clazz);
            try {
                segmentation = (IParticiple)Class.forName(clazz).newInstance();
                pool.put(clazz, segmentation);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                LOGGER.info("构造分词实现类失败：", e);
            }
        }
        return segmentation;
    }
}