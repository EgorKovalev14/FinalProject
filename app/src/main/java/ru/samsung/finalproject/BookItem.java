package ru.samsung.finalproject;

public class BookItem {
    private int id;
    private String name;
    private boolean read;
    private String filePath;
    private int content_id;
    private int scroll;

    public BookItem(String name, boolean read, String filePath, int content_id, int scroll) {
        this.name = name;
        this.read = read;
        this.filePath = filePath;
        this.content_id = content_id;
        this.scroll = scroll;
    }

    public BookItem(int id, String name, int content_id, int scroll) {
        this.id = id;
        this.name = name;
        this.content_id = content_id;
        this.scroll = scroll;
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", read=" + read +
                ", filePath='" + filePath + '\'' +
                ", content_id=" + content_id +
                ", scroll=" + scroll +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }
}
