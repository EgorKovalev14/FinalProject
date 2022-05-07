package ru.samsung.finalproject;

public class ProgressFromDB {
    int id;
    int progress;

    public ProgressFromDB(int id, int progress) {
        this.id = id;
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
}
