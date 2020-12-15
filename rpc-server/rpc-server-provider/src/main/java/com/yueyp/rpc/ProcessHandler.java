package com.yueyp.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandler implements Runnable{

    private Socket socket;

    private Object service;

    public ProcessHandler(Object service, Socket socket){
        this.service = service;
        this.socket = socket;
    }
    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();

            // 根据请求信息调用，本地服务
            Object result = invokeService(service, rpcRequest);

            // 将调用结果写回调用方
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {

            if (objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //

    /**
     * 根据请求内容反射调用具体的本地服务,因为外部只能拿到接口信息，这里只能通过反射实现调用
     * @param service
     * @param rpcRequest
     * @return
     */
    public Object invokeService(Object service, RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Object[] parameters = rpcRequest.getParamters();

        // 获取参数类型
        Class[] types = new Class[parameters.length];
        for (int i=0; i < parameters.length; i++){
            types[i] = parameters[i].getClass();
        }

        Class clazz = Class.forName(rpcRequest.getClassName());
        String methodName = rpcRequest.getMethodName();
        Method method = clazz.getMethod(methodName, types);

        /**
         * 反射调用
         * 注：clazz 是接口，这里不能直接使用clazz.newInstance()，需要将与当前接口相关的实现类传进来，
         * 或者使用将接口和实现类的关系保存到缓存 ConcurrentHashMap ,根据接口取实现类
         */
        Object result = method.invoke(service, parameters);
        return result;
    }

}
