package com.yueyp.rpc;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        IHelloService service = new HelloServiceImpl();

        RpcServerProxy serverProxy = new RpcServerProxy();
        serverProxy.publisher(service, 8080);
        System.out.println( "RPC SERVER STAR......" );
    }
}
