package com.yxy.xmind.to.md;

import com.yxy.xmind.util.XMindUtils;
import org.codehaus.plexus.util.StringUtils;
import org.xmind.core.ITopic;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

public class ToMdUtils {

    private ToMdUtils() {
    }

    /**
     *
     * @param filePath .xmind 文件位置
     * @param stringBuilderConsumer 处理响应的函数 （注意会多次调用）
     * @throws IOException
     */
    public static void toMD(String filePath, Consumer<StringBuilder> stringBuilderConsumer) throws IOException {
        File file = new File(filePath);
        String unPath = com.util.ZipUtils.unZipFiles(file, file.getParentFile().getPath());
        ITopic iTopic = XMindUtils.readRootTopic(unPath);
        if (iTopic == null) {
            System.out.println("Read error.");
            return;
        }

        ToMdUtils.iTopicToString(iTopic, 0, stringBuilderConsumer);
    }

    private static void iTopicToString(ITopic iTopic, int level, Consumer<StringBuilder> consumer) {
        String titleText = iTopic.getTitleText();
        if (StringUtils.isNotBlank(titleText.trim())) {
            // 获得MD语法
            GetTop get = new GetTop(level, titleText).invoke();

            setLink(iTopic, get);

            StringBuilder str = new StringBuilder();
            if (get.getTextType() == TextType.TITLE && level > 0) {
                // 跟上一行保持距离
                str.append("\n");
            }

            // 处理标题
            addThis(get, str);
            // 处理 Labels
            addLabels(iTopic, get, str);
            // 处理图片
            addImage(iTopic, str);

            // 调用处理函数
            consumer.accept(str);
        }
        // 递归
        iTopic.getAllChildren().forEach(i -> iTopicToString(i, level + 1, consumer));
    }

    private static void addImage(ITopic iTopic, StringBuilder str) {
        String source = iTopic.getImage().getSource();
        if (source != null) {
            str.append("![image](" + source + ")\n");
        }
    }

    private static void setLink(ITopic iTopic, GetTop get) {
        String hyperlink = iTopic.getHyperlink();
        if (hyperlink != null) {
            get.getGrammar().append("[");
            get.getEnd().insert(0, "](" + hyperlink + ")");
        }
    }

    private static void addThis(GetTop get, StringBuilder str) {
        for (String t : get.getTitle().split("\n")) {
            if (t.trim().length() == 0) {
                continue;
            }
            str.append(get.getAgainst()).append(get.getGrammar()).append(t).append(get.getEnd());
        }
    }

    private static void addLabels(ITopic iTopic, GetTop getTop, StringBuilder str) {
        Set<String> labels = iTopic.getLabels();
        if (labels != null && labels.size() > 0) {
            labels.forEach(i -> {
                for (String s : i.split(",")) {
                    str.append(getTop.getAgainst()).append("**").append(s).append("**  \n");
                }
            });
        }
    }

    private static class GetTop {
        private final int level;
        private final String title;
        private final StringBuilder grammar = new StringBuilder();
        private final StringBuilder against = new StringBuilder();
        private StringBuilder end = new StringBuilder();
        private TextType textType;

        GetTop(int level, String title) {
            this.level = level;
            this.title = title;
        }

        public TextType getTextType() {
            return textType;
        }

        public void setEnd(StringBuilder end) {
            this.end = end;
        }

        public StringBuilder getAgainst() {
            return against;
        }

        StringBuilder getGrammar() {
            return grammar;
        }

        StringBuilder getEnd() {
            return end;
        }

        public String getTitle() {
            return title;
        }

        GetTop invoke() {
            int maxLevel = 3;
            if (level < maxLevel) {
                // 标题
                textType = TextType.TITLE;
                addStr("#", level, grammar);
                grammar.append(" ");
                end.append("\n");
            } else {
                // 标签
                textType = TextType.LI;
                addStr("\t", level - maxLevel - 1, against);
                grammar.append("- ");
                end.append("  \n");
            }
            return this;
        }

        private void addStr(String str, int num, StringBuilder grammar) {
            for (int i = 0; i <= num; i++) {
                grammar.append(str);
            }
        }
    }
}
