package com.yueyp.rpc;

import java.lang.reflect.Proxy;

/**
 * 负责生成一个服务端调用的动态代理对象
 */
public class RpcClientProxy {

    /**
     * 根据服务对象生成代理类信息，根据代理类调用远端服务。调用service的方法时，实际是调用的 RpcInvocationHandler 的invoke方法
     * @param service
     * @param <T>
     * @return
     */
    public <T> T getProxy(Class<T> service){
//        new Class<?>[]{service}  service.getClass().getInterfaces()
        if (service.getClass().equals(service)){
            System.out.println("true ======");
        }
        return (T) Proxy.newProxyInstance(service.getClassLoader(),
                new Class<?>[]{service}, new RpcInvocationHandler("localhost", 8080));
    }
}
