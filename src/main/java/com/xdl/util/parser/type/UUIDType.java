package com.xdl.util.parser.type;

import java.util.UUID;

public class UUIDType implements SpecifyType {

    @Override
    public Object def() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Object random() {
        return UUID.randomUUID().toString();
    }

}
