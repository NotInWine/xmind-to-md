package com.yxy.to.md;

import com.yxy.to.md.core.AbstractToMD;
import com.yxy.util.XMindUtils;
import org.codehaus.plexus.util.StringUtils;
import org.xmind.core.ITopic;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 工具类
 * @author yangchao
 */
public class XMindToMdUtils extends AbstractToMD {

    private static XMindToMdUtils toMdUtils = new XMindToMdUtils();

    private XMindToMdUtils() {
    }

    public static XMindToMdUtils getInstance() {
        return toMdUtils;
    }

    /**
     *
     * @param filePath .xmind 文件位置
     * @param stringBuilderConsumer 处理响应的函数 （注意会多次调用）
     * @throws IOException
     */
    public void toMD(String filePath, Consumer<StringBuilder> stringBuilderConsumer) throws IOException {
        File file = new File(filePath);
        String unPath = com.util.ZipUtils.unZipFiles(file, file.getParentFile().getPath());
        ITopic iTopic = XMindUtils.readRootTopic(unPath);
        if (iTopic == null) {
            System.out.println("Read error.");
            return;
        }

        iTopicToString(iTopic, 0, stringBuilderConsumer);
    }

    private void iTopicToString(ITopic iTopic, int level, Consumer<StringBuilder> consumer) {
        String titleText = iTopic.getTitleText();
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
        iTopic.getAllChildren().forEach(i -> iTopicToString(i, level + 1, consumer));
    }
}
