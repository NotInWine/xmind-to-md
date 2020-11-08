package com.yxy.demo;

import com.yxy.to.md.ToMdUtils;

import java.io.IOException;

public class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        // xmind
        ToMdUtils.toMD(
                "C:\\Users\\xxx\\Downloads\\Redis-test.xmind",
                i -> System.out.print(i.toString())
        );

        // pos
        ToMdUtils.toMD(
                "C:\\Users\\xxx\\Downloads\\Redis.pos",
                i -> System.out.print(i.toString())
        );
    }
}
