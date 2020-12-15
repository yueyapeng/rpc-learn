package com.yueyp.rpc;

import java.io.Serializable;

/**
 * Rpc 请求对象
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -6001898952151287644L;

    private String className;

    private String methodName;

    private Object[] paramters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParamters() {
        return paramters;
    }

    public void setParamters(Object[] paramters) {
        this.paramters = paramters;
    }
}
