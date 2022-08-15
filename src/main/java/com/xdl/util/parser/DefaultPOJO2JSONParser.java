package com.xdl.util.parser;


import com.xdl.util.parser.type.SpecifyType;

public class DefaultPOJO2JSONParser extends POJO2JSONParser {

    @Override
    protected Object getFakeValue(SpecifyType specifyType) {
        return specifyType.def();
    }
}
