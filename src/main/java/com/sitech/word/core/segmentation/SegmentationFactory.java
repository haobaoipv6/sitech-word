package com.sitech.word.core.segmentation;

import java.util.HashMap;
import java.util.Map;

public class SegmentationFactory {
    private static final Map<String, ISegmentation> pool = new HashMap<String, ISegmentation>();
    private SegmentationFactory(){};
    
    public static synchronized ISegmentation getSegmentation(SegmentationAlgorithm segmentationAlgorithm){
        String clazz = "com.sitech.word.core.segmentation.impl."+segmentationAlgorithm.name();
        ISegmentation segmentation = pool.get(clazz);
        if(segmentation == null){
            try {
                segmentation = (ISegmentation)Class.forName(clazz).newInstance();
                pool.put(clazz, segmentation);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            	ex.printStackTrace();
            }
        }
        return segmentation;
    }
}