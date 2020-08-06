# XMind To Markdown
XMind思维导图转Markdown文本
## 效果
- 效果图1
![image](src/main/resources/x1.png)
- 效果图
![image](src/main/resources/mdcode.png)
- 原始脑图
![image](src/main/resources/pos.png)
## Demo
[Deom](src/main/java/com/yxy/xmind/demo/Main.java)
```java
package com.yxy.xmind.demo;

import com.yxy.xmind.to.md.ToMdUtils;

import java.io.IOException;

public class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\yangchao\\Downloads\\Redis-test.xmind";
        ToMdUtils.toMD(filePath, i -> System.out.print(i.toString()));
    }
}
```
## 限制
1. 需要有文件目录的写权限  
    *因为需要解压XMind文件读取内部xml*
2. 暂时不支持读取remark  
    *因为解析遇到点问题，有会的朋友可以push一下。或者留言*
