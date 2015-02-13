package com.sitech.analyzer.corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将多个语料库文件合并为一个
 */
public class CorpusMerge {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpusMerge.class);
    
    /**
     * 将多个语料库文件合并为一个
     * @param source 目录，可多级嵌套
     * @param target 目标文件
     * @throws IOException 
     */
    public static void merge(String source, String target) throws IOException{
        final AtomicLong count = new AtomicLong();
        final AtomicLong lines = new AtomicLong();
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target),"utf-8"))){
            Files.walkFileTree(Paths.get(source), new SimpleFileVisitor<Path>(){

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        LOGGER.info("处理文件："+file);
                        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile()),"utf-8"));){
                            String line;
                            while( (line = reader.readLine()) != null ){
                                count.addAndGet(line.length());
                                lines.incrementAndGet();
                                writer.write(line+"\n");
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                    
                });
        }
        LOGGER.info("语料库行数："+lines.get());
        LOGGER.info("语料库字符数目："+count.get());
    }
    
    public static void main(String[] args) throws IOException{
        //注意输入语料库的文件编码要为UTF-8
        String source = "d:/corpora";
        String target = "src/main/resources/corpus/corpus.txt";
        merge(source, target);
    }
}
