package com.yxy.to.md;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxy.to.md.core.AbstractToMD;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * 工具类
 *
 * @author yangchao
 */
public class PosToMd extends AbstractToMD implements ToMdInterface {

    private static final PosToMd TO_MD_UTILS = new PosToMd();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private PosToMd() {
    }

    protected static PosToMd getInstance() {
        return TO_MD_UTILS;
    }

    /**
     * @param filePath              .xmind 文件位置
     * @param stringBuilderConsumer 处理响应的函数 （注意会多次调用）
     * @throws IOException
     */
    @Override
    public void toMD(String filePath, Consumer<StringBuilder> stringBuilderConsumer) throws IOException {
        String json = IOUtil.toString(new FileInputStream(new File(filePath)));

        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);
        jsonNodeToString(jsonNode, 0, stringBuilderConsumer);
    }

    /**
     * 先根遍历序递归删除文件夹
     *
     * @param dirFile 要被删除的文件或者目录
     * @return 删除成功返回true, 否则返回false
     */
    private static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    private void jsonNodeToString(JsonNode jsonNode, int level, Consumer<StringBuilder> consumer) {
        /*if (level == 0) {
            jsonNode = jsonNode.get("diagram").get("elements");
        }
        String titleText = jsonNode.get("diagram").get("");
        if (StringUtils.isNotBlank(titleText.trim())) {
            // 获得MD语法
            GetTop get = new GetTop(level, titleText).invoke();

            // 处理超连接
            String hyperlink = iTopic.getHyperlink();
            setLink(hyperlink, get);

            StringBuilder str = new StringBuilder();
            // 处理标题
            addThis(get, str, level);

            // 处理 Labels
            Set<String> labels = iTopic.getLabels();
            addLabels(labels, get, str);

            // 处理图片
            String source = iTopic.getImage().getSource();
            addImage(source, str);

            // 调用处理函数
            consumer.accept(str);
        }
        // 递归
        iTopic.getAllChildren().forEach(i -> jsonNodeToString(i, level + 1, consumer));*/
    }
}
