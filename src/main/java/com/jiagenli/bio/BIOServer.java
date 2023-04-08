package com.jiagenli.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 启动一个线程池
        Executor executor = Executors.newCachedThreadPool();
        // 建立Socket
        ServerSocket serverSocket = new ServerSocket(6666);
        // 创建server，监听连接，处理数据
        System.out.println("客户端已经启动了");
        while (true) {
            System.out.println("等待连接");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            // 对每一个连接分配一个线程
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    private static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            System.out.println("当前处理的线程id是：" + Thread.currentThread().getId() + "当前处理的线程name是："
                    + Thread.currentThread().getName());
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read, StandardCharsets.UTF_8));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
