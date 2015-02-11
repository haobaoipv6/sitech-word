package com.sitech.word;

import com.sitech.word.pojo.Word;

public interface Task {

    /**
     * unique id for a task.
     *
     * @return uuid
     */
    public String getUUID();

    /**
     * site of a task
     *
     * @return site
     */
    public Word getText();

}