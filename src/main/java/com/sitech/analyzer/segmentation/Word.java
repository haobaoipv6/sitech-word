package com.sitech.analyzer.segmentation;

import java.util.Objects;

/**
 * 词
 * Word
 * @author hb
 */
public class Word {
    private String text;
    public Word(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.text);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        return Objects.equals(this.text, other.text);
    }
    @Override
    public String toString(){
        return text;
    }
}
