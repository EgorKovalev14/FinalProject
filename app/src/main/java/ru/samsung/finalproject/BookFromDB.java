package ru.samsung.finalproject;

    public class BookFromDB {
        int id;
        String bookFromDBName;
        int content_id;
        int scroll;

        public void setBookFromDBName(String bookFromDBName) {
            this.bookFromDBName = bookFromDBName;
        }

        public BookFromDB(int id, String bookFromDBName, int content_id, int scroll ) {
            this.id = id;
            this.bookFromDBName = bookFromDBName;
            this.content_id= content_id;
            this.scroll = scroll;
        }

        @Override
        public String toString() {
            return  bookFromDBName;
        }

        public int getId() {
            return id;
        }

        public int getContent_id() {
            return content_id;
        }

        public int getScroll() {
            return scroll;
        }

        public String getBookFromDBName() {
            return bookFromDBName;
        }
    }
