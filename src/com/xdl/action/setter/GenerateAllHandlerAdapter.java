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

/**
 * @author Bx_Hu
 */
public class GenerateAllHandlerAdapter implements GenerateAllHandler {
    @Override
    public boolean shouldAddDefaultValue() {
        return false;
    }

    @Override
    public boolean isSetter() {
        return true;
    }

    @Override
    public boolean isFromMethod() {
        return false;
    }

    @Override
    public String formatLine(String line) {
        return line;
    }

    @Override
    public boolean forBuilder() {
        return false;
    }

    @Override
    public boolean isAccessors() {
        return false;
    }


}
