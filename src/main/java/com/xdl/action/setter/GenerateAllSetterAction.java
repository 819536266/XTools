/*
 *  Copyright (c) 2017-2019, bruce.ge.
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; version 2 of
 *    the License.
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *    GNU General Public License for more details.
 *    You should have received a copy of the GNU General Public License
 *    along with this program;
 */

package com.xdl.action.setter;


import com.xdl.constant.CommonConstants;
import org.jetbrains.annotations.NotNull;

/**
 * Created by bruce.ge on 2016/12/23.
 */
public class GenerateAllSetterAction extends GenerateAllSetterBase {


    public GenerateAllSetterAction() {
        super(new GenerateAllHandlerAdapter() {
            @Override
            public boolean shouldAddDefaultValue() {
                return true;
            }
        });
    }

    @NotNull
    @Override
    public String getText() {
        return CommonConstants.GENERATE_SETTER_METHOD;
    }
}
