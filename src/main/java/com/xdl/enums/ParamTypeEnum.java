package com.xdl.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bx_Hu
 */

public enum ParamTypeEnum {
    /**
     * 请求参数类型
     */
    TEXT("文本"), BODY("BODY"), FILE("文件");

    /**
     * 参数类型名称
     */
    @Getter
    private final String name;

    ParamTypeEnum(String name) {
        this.name = name;
    }

    public static ParamTypeEnum getParamTypeEnum(Object name) {
        ParamTypeEnum[] values = ParamTypeEnum.values();
        for (ParamTypeEnum value : values) {
            if (value.getName()
                    .equals(name)) {
                return value;
            }
        }
        return ParamTypeEnum.TEXT;
    }

}
