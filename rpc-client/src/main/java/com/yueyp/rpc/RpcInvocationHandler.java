package com.yueyp.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcInvocationHandler implements InvocationHandler {

    private String host;

    private int port;

    public RpcInvocationHandler(String host, int port){
        this.host = host;
        this.port = port;
    }


    /**
     * 具体调用远端服务
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 组装请求参数
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamters(args);

        // 链接服务端，发送调用请求
        RpcNetTransport rpcNetTransport = new RpcNetTransport(host, port);
        Object result = rpcNetTransport.sendRequest(rpcRequest);
        return result;
    }
}
