package com.yxy.to.md;

public class ToMdUtils {

    private ToMdUtils() {
    }

    public static ToMdInterface getInterface(String filePath){
        String[] split = filePath.split("\\.");
        ToMdInterface instance;
        if (split[split.length - 1].equalsIgnoreCase("pos")) {
            instance = PosToMd.getInstance();
        } else {
            instance = XMindToMd.getInstance();
        }
        return instance;
    }
}
