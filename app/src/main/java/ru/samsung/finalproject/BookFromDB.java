package ru.samsung.finalproject;

    public class BookFromDB {
        int id;
        String bookFromDBName;

        public void setBookFromDBName(String bookFromDBName) {
            this.bookFromDBName = bookFromDBName;
        }

        public BookFromDB(int id, String bookFromDBName) {
            this.id = id;
            this.bookFromDBName = bookFromDBName;
        }

        @Override
        public String toString() {
            return  bookFromDBName;
        }

        public int getId() {
            return id;
        }

        public String getBookFromDBName() {
            return bookFromDBName;
        }
    }
