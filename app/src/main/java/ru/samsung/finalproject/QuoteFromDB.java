package ru.samsung.finalproject;

public class QuoteFromDB {
    String bookName;
    String quote;

    public QuoteFromDB(String bookName, String quote) {
        this.bookName = bookName;
        this.quote = quote;
    }

    @Override
    public String toString() {
        return '\'' + quote + '\'' + " " + "-" + " " + bookName ;

    }
}
