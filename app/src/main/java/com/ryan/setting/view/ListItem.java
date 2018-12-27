package com.ryan.setting.view;

public class ListItem {
    private String title;
    private String content;
    private int enableIcon;
    private int disableIcon;


    public ListItem(int enableIcon, int disableIcon, String title, String content) {
        this.title = title;
        this.content = content;
        this.enableIcon = enableIcon;
        this.disableIcon = disableIcon;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getIcon(boolean isEnable) {
        if (isEnable) {
            return enableIcon;
        }
        else {
            return disableIcon;
        }

    }

}
