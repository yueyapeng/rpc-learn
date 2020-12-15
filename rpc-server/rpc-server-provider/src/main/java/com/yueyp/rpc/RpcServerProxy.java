package com.yueyp.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 将服务暴露出去，即启动一个serverSocket服务，等待客户端链接
 */
public class RpcServerProxy {

    public ExecutorService executorService = Executors.newCachedThreadPool();
    /**
     * 发布服务
     * @param port
     */
    public void publisher(Object service, int port){
        ServerSocket serverSocket = null;

        int i=0;
        try {
            serverSocket = new ServerSocket(port);

            // 处理客户端链接
            while (true) {
                System.out.println( "RPC SERVER wait for client[" + i + "]accept......" );
                Socket socket = serverSocket.accept();
                System.out.println( "client [" + i + "]accept" );
                // 采用多线程处理客户端请求，提高性能
                executorService.submit(new ProcessHandler(service, socket));
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
