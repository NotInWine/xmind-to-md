package com.yxy.to.md.core;

import java.util.Set;

/**
 * 抽象父类
 * @author yangchao
 */
public abstract class AbstractToMD {

    protected void addImage(String source, StringBuilder str) {
        if (source != null) {
            str.append("![image](" + source + ")\n");
        }
    }

    protected void setLink(String hyperlink, GetTop get) {
        if (hyperlink != null) {
            get.getGrammar().append("[");
            get.getEnd().insert(0, "](" + hyperlink + ")");
        }
    }

    protected void addThis(GetTop get, StringBuilder str, int level) {
        if (get.getTextType() == TextType.TITLE && level > 0) {
            // 跟上一行保持距离
            str.append("\n");
        }

        for (String t : get.getTitle().split("\n")) {
            if (t.trim().length() == 0) {
                continue;
            }
            str.append(get.getAgainst()).append(get.getGrammar()).append(t).append(get.getEnd());
        }
    }

    protected void addLabels(Set<String> labels, GetTop getTop, StringBuilder str) {

        if (labels != null && labels.size() > 0) {
            labels.forEach(i -> {
                for (String s : i.split(",")) {
                    str.append(getTop.getAgainst()).append("**").append(s).append("**  \n");
                }
            });
        }
    }

    protected static class GetTop {
        private final int level;
        private final String title;
        private final StringBuilder grammar = new StringBuilder();
        private final StringBuilder against = new StringBuilder();
        private StringBuilder end = new StringBuilder();
        private TextType textType;

        public GetTop(int level, String title) {
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

        public StringBuilder getGrammar() {
            return grammar;
        }

        public StringBuilder getEnd() {
            return end;
        }

        public String getTitle() {
            return title;
        }

        public GetTop invoke() {
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
