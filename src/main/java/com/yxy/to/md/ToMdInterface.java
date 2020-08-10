package com.yxy.to.md;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * 转换接口
 * @author yangchao
 */
public interface ToMdInterface {

    /**
     * 转换为MD文件输出到 stringBuilderConsumer
     * @param filePath
     * @param stringBuilderConsumer
     */
    void toMD(String filePath, Consumer<StringBuilder> stringBuilderConsumer) throws IOException;
}
