# XMind To Markdown
1. XMind思维导图转Markdown文本
2. [Processon](https://www.processon.com/;jsessionid=779C56220415B43892F9C36E2CB82E9C.jvm1)思维导图转Markdown文本
## 效果
- 效果图1
![image](src/main/resources/x1.png)
- 效果图
![image](src/main/resources/mdcode.png)
- 原始脑图
![image](src/main/resources/pos.png)
## Demo
[Deom](src/main/java/com/yxy/demo/Main.java)
```java
package com.yxy.demo;

import com.yxy.to.md.XMindToMd;
import java.io.IOException;

public class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        // xmind
        ToMdUtils.toMD(
                "C:\\Users\\yangchao\\Downloads\\Redis-test.xmind",
                i -> System.out.print(i.toString())
        );

        // pos
        ToMdUtils.toMD(
                "C:\\Users\\yangchao\\Downloads\\Redis.pos",
                i -> System.out.print(i.toString())
        );
    }
}
```
## 支持
- 超链接
- 图片
- 多层级主题
- 多行主题转列表
- 标签（Labels）

## 限制(Xmind)
1. 需要有文件目录的写权限  
    *因为需要解压XMind文件读取内部xml*
2. 暂时不支持读取remark  
    *因为解析遇到点问题，有会的朋友欢迎push一下。或者留言。不胜感谢๑乛◡乛๑*
