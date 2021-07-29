package com.xdl.model;

import lombok.Data;


/**
 * @author Bx_Hu
 */
@Data
public class Row {

    private final String title;
    private final String comment;

    private final String fileName;

    private final String fileType;

    private final String content;


    public Row(String title, String comment, String fileName, String fileType, String content) {
        this.title = title;
        this.comment = comment;
        this.fileName = fileName;
        this.fileType = fileType;
        this.content = content;
    }


}
