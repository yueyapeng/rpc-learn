package com.yueyp.rpc;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        RpcClientProxy rpcClientProxy = new RpcClientProxy();
        IHelloService helloService = rpcClientProxy.getProxy(IHelloService.class);
        Object result = helloService.sayHello(" this is yueyp's rpc test");
        System.out.println( "【Rpc client invoke result】: " + result );
    }
}
