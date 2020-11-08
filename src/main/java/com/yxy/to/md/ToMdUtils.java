package com.yxy.to.md;

import java.io.IOException;
import java.util.function.Consumer;

public class ToMdUtils {

    private ToMdUtils() {
    }

    public static ToMdInterface toMD(String filePath, Consumer<StringBuilder> stringBuilderConsumer) throws IOException {
        String[] split = filePath.split("\\.");
        ToMdInterface instance;
        if (split[split.length - 1].equalsIgnoreCase("pos")) {
            instance = PosToMd.getInstance();
        } else {
            instance = XMindToMd.getInstance();
        }
        instance.toMD(filePath, stringBuilderConsumer);
        return instance;
    }
}
