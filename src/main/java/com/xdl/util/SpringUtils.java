package com.xdl.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.Method;
import com.xdl.model.SpringRequestMethodAnnotation;
import com.xdl.model.XHttpParam;
import lombok.Data;

import java.util.List;


/**
 * @author Bx_Hu
 */
@Data
public class SpringUtils {

    /**
     * Spring注解
     */
    public static final String POST_MAPPING = "@PostMapping";
    public static final String GET_MAPPING = "@GetMapping";
    public static final String DELETE_MAPPING = "@DeleteMapping";
    public static final String PUT_MAPPING = "@PutMapping";
    public static final String PATCH_MAPPING = "@PatchMapping";
    public static final String REQUEST_MAPPING = "@RequestMapping";

    public static final String MAPPING_PARAM_VALUE = "value";

    /**
     * RequestMapping  param 参数
     */
    public static final String REQUEST_MAPPING_METHOD_GET = "RequestMethod.GET";
    public static final String REQUEST_MAPPING_METHOD_POST = "RequestMethod.POST";
    public static final String REQUEST_MAPPING_METHOD_DELETE = "RequestMethod.DELETE";
    public static final String REQUEST_MAPPING_METHOD_PUT = "RequestMethod.PUT";
    public static final String REQUEST_MAPPING_METHOD_PATCH = "RequestMethod.PATCH";

    /**
     * 文件上传类型
     */
    public static final String MULTIPART_FILE_CLASS_PATH = "org.springframework.web.multipart.MultipartFile";
    public static final String MULTIPART_FILES_CLASS_PATH = "org.springframework.web.multipart.MultipartFile[]";
    /**
     * RequestBody
     */
    public static final String REQUEST_BODY_CLASS_PATH = "org.springframework.web.bind.annotation.RequestBody";


    /**
     * 包含RequestMapping数组
     */
    public static final SpringRequestMethodAnnotation[] METHOD_TYPE = {
            SpringRequestMethodAnnotation.GET_MAPPING,
            SpringRequestMethodAnnotation.POST_MAPPING,
            SpringRequestMethodAnnotation.DELETE_MAPPING,
            SpringRequestMethodAnnotation.PUT_MAPPING,
            SpringRequestMethodAnnotation.PATCH_MAPPING,
            SpringRequestMethodAnnotation.REQUEST_MAPPING,
    };

    /**
     * 不包含RequestMapping数组
     */
    public static final SpringRequestMethodAnnotation[] NO_REQUEST_METHOD_TYPE = {
            SpringRequestMethodAnnotation.GET_MAPPING,
            SpringRequestMethodAnnotation.POST_MAPPING,
            SpringRequestMethodAnnotation.DELETE_MAPPING,
            SpringRequestMethodAnnotation.PUT_MAPPING,
            SpringRequestMethodAnnotation.PATCH_MAPPING,
    };


    /**
     * RequestMapping param 参数数组
     */
    public static final String[] REQUEST_PARAM_METHOD = {
            REQUEST_MAPPING_METHOD_GET,
            REQUEST_MAPPING_METHOD_POST,
            REQUEST_MAPPING_METHOD_DELETE,
            REQUEST_MAPPING_METHOD_PUT,
            REQUEST_MAPPING_METHOD_PATCH,
    };





    public static String getMethodText(Method method) {
        if (method == null) {
            return REQUEST_MAPPING;
        }
        switch (method) {
            case GET:
                return GET_MAPPING;
            case POST:
                return POST_MAPPING;
            case DELETE:
                return DELETE_MAPPING;
            case PUT:
                return PUT_MAPPING;
            case PATCH:
                return PATCH_MAPPING;
            default:
                return REQUEST_MAPPING;
        }
    }

    /**
     *
     * @param methodValue 根据 RequestMapping methodValue 获取对应Mapping
     * @return  SpringRequestMethodAnnotation
     */
    public static SpringRequestMethodAnnotation getMethodParamMapping(String methodValue) {
        if (methodValue == null) {
            return null;
        }
        switch (methodValue) {
            case REQUEST_MAPPING_METHOD_GET:
                return SpringRequestMethodAnnotation.GET_MAPPING;
            case REQUEST_MAPPING_METHOD_POST:
                return SpringRequestMethodAnnotation.POST_MAPPING;
            case REQUEST_MAPPING_METHOD_DELETE:
                return SpringRequestMethodAnnotation.DELETE_MAPPING;
            case REQUEST_MAPPING_METHOD_PUT:
                return SpringRequestMethodAnnotation.PUT_MAPPING;
            case REQUEST_MAPPING_METHOD_PATCH:
                return SpringRequestMethodAnnotation.PATCH_MAPPING;
            default:
                return null;
        }
    }

    /**
     * restful风格路径
     *
     * @param url         原url
     * @param xHttpParams xHttpParams
     * @return String
     */
    public static String restful(String url, List<XHttpParam> xHttpParams) {
        if (ObjectUtil.isEmpty(url) || CollUtil.isEmpty(xHttpParams)) {
            return url;
        }
        for (XHttpParam xHttpParam : xHttpParams) {
            if (xHttpParam.getIsCheck() && xHttpParam.getValue() instanceof String) {
                url = url.replace("{" + xHttpParam.getName() + "}", (String)xHttpParam.getValue());
            }
        }
        return url;
    }



    /**
     * 判断是否是基础数据类型，即 int,double,long等类似格式
     */
    public static boolean isCommonDataType(Class clazz){
        return clazz.isPrimitive();
    }

    /**
     * 判断是否是基础数据类型的包装类型
     *
     */
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
