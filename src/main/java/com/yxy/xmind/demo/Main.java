package com.yxy.xmind.demo;

import com.yxy.xmind.to.md.ToMdUtils;

import java.io.IOException;

public class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        ToMdUtils.toMD(
                // xmind 文件位置
                "C:\\Users\\yangchao\\Downloads\\Redis-test.xmind",
                // 打印
                i -> System.out.print(i.toString())
        );
    }
}
