/**
 * Protobuf Module
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.protobuf;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

import java.io.InputStream;
import java.lang.reflect.Method;

public class InputStreamToProtobuf extends AbstractTransformer
{
    public InputStreamToProtobuf() {
        this.registerSourceType(DataTypeFactory.create(InputStream.class));
    }

    @Override
    protected Object doTransform(Object src, String enc) throws TransformerException
    {
        Class returnType = getReturnDataType().getType();
        try {
            Method parseFromMethod = returnType.getDeclaredMethod("parseFrom", InputStream.class);
            Object protobufResult = parseFromMethod.invoke(null, src);
            return protobufResult;
        } catch(Exception e) {
            throw new TransformerException(this, e);
        }
    }
}