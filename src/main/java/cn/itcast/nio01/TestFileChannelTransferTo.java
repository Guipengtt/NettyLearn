package cn.itcast.nio01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
             FileChannel to = new FileOutputStream("to.txt").getChannel()) {
            // 效率高 (底层使用操作系统的零拷贝进行优化)，最多传2g数据
            long size = from.size();
            // left 变量 代表还剩余多少字节
            // 分多次传输大于2g的数据
            for(long left = size; left >0;) {
                System.out.println("position: " + (size - left) + " left: " + left);
                left -= from.transferTo(size - left, left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
