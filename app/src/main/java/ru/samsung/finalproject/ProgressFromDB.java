package ru.samsung.finalproject;

public class ProgressFromDB {
    int id;
    int book;
    int progress;

    public ProgressFromDB(int id, int book, int progress) {
        this.id = id;
        this.book= book;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Integer getBook() {
        return book;
    }
}
