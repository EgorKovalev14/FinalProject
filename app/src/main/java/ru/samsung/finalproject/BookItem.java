package ru.samsung.finalproject;

public class BookItem {
    String name;
    boolean read;
    String filePath;
    int content_id;
    int scroll;

    public BookItem(String name, boolean read, String filePath, int content_id, int scroll) {
        this.name = name;
        this.read = read;
        this.filePath = filePath;
        this.content_id = content_id;
        this.scroll=scroll;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getContent_id() {
        return content_id;
    }

    public int getScroll() {
        return scroll;
    }
}
