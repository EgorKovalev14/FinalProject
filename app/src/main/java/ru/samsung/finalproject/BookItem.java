package ru.samsung.finalproject;

public class BookItem {
    String name;
    boolean read;
    String filePath;

    public BookItem(String name, boolean read, String filePath) {
        this.name = name;
        this.read = read;
        this.filePath = filePath;
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

}
