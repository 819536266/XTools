package com.xdl.model;

import com.xdl.enums.ParamTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author huboxin
 * @title: XHttpParam
 * @projectName XHttp
 * @description: 请求参数
 * @date 2020/5/2710:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class XHttpParam {

    /**
     * 是否选中
     */
    private Boolean isCheck = true;

    /**
     * 属性
     */
    private String name;

    /**
     * 参数
     */
    private Object value = "";

    /**
     * 文件
     */
    private String file;

    /**
     * body
     */
    private String body;

    /**
     * 类型 1 文字 2文件 3 json
     */
    private ParamTypeEnum paramTypeEnum = ParamTypeEnum.TEXT;

    /**
     * 参数类型
     */
    private String classType;
}
