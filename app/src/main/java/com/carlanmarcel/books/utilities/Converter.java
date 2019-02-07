package com.carlanmarcel.books.utilities;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.model.ResponseBook;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Converter {

    public static String convertDoubleToOneDecimalString(Double number ){
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(number);
    }

    public static String[] convertEnumToStringArray(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }

    public static Book convertResponseBookToBook(ResponseBook responseBook){
         return new Book(
                responseBook.getTitle(),
                responseBook.getAuthor(),
                responseBook.getRating(),
                responseBook.getBookCategory(),
                responseBook.getId()
        );
    }
    public static List<Book> convertResponseBookToBook(List<ResponseBook> books){
        List<Book> newBooks = new ArrayList<>();
        for (ResponseBook responseBook:books) {
            newBooks.add(convertResponseBookToBook(responseBook));
        }
        return newBooks;
    }


    public static List<ResponseBook> convertToResponseBook(List<Book> books){
        List<ResponseBook> responseBooks = new ArrayList<>();
        for (Book book:books) {
            ResponseBook responseBook = new ResponseBook();
            responseBook.setTitle(book.getTitle());
            responseBook.setAuthor(book.getAuthor());
            responseBook.setRating(book.getRating());
            responseBook.setBookCategory(book.getBookCategory());
            responseBooks.add(responseBook);
        }
        return responseBooks;
    }
}
