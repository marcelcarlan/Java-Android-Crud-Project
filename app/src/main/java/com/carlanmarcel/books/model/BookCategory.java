package com.carlanmarcel.books.model;

public enum  BookCategory {
    Tech{
        public int getIndex() {
            return 0;
        }
    },
    Cooking{
        public int getIndex() {
            return 1;
        }
    },
    Fiction{
        public int getIndex() {
            return 2;
        }
    },
    Travel{
        public int getIndex() {
            return 3;
        }
    };
}
