package com.xdl.enums;

import cn.hutool.http.Method;
import com.xdl.util.Icons;
import com.xdl.util.SpringUtils;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Bx_Hu
 */
public enum MethodEnum {

    /**
     *
     */
    GET(Method.GET, SpringUtils.GET_MAPPING, Icons.METHOD_GET),
    POST(Method.POST, SpringUtils.POST_MAPPING, Icons.METHOD_POST),
    PUT(Method.PUT, SpringUtils.PUT_MAPPING, Icons.METHOD_PUT),
    DELETE(Method.POST, SpringUtils.DELETE_MAPPING, Icons.METHOD_POST),
    PATCH(Method.PATCH, SpringUtils.PATCH_MAPPING, Icons.METHOD_PATCH);

    /**
     * 请求方式
     */
    @Getter
    @Setter
    private Method method;

    /**
     * spring mapping
     */
    @Getter
    @Setter
    private String mapping;

    /**
     * 对应图标
     */
    @Getter
    @Setter
    private Icon icon;

    MethodEnum(Method method, String mapping, Icon icon) {
        this.method = method;
        this.mapping = mapping;
        this.icon = icon;
    }

    /**
     * 获取方法对应的图标
     *
     * @param method 请求类型
     * @return icon
     */
    @NotNull
    public static Icon getMethodIcon(Method method) {
        for (MethodEnum value : MethodEnum.values()) {
            if (value.getMethod()
                    .equals(method)) {
                return value.icon;
            }
        }
        return Icons.METHOD_UNDEFINED;
    }

}
