package com.xdl.util.parser;


import com.xdl.util.parser.type.SpecifyType;

public class RandomPOJO2JSONParser extends POJO2JSONParser {

    @Override
    protected Object getFakeValue(SpecifyType specifyType) {
        return specifyType.random();
    }
}
