package com.carlanmarcel.books.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.service.MainService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.carlanmarcel.books.utilities.Constants.NOT_VALID_RATING;

public class EditorViewModel extends AndroidViewModel {


    public MutableLiveData<Book> mLiveBook = new MutableLiveData<>();

    private MainService mainService;

    private Executor executor = Executors.newSingleThreadExecutor();


    public EditorViewModel(@NonNull Application application) {
        super(application);
        mainService = MainService.getInstance(getApplication());
    }


    public void loadData(final Long bookId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
               Book book = mainService.getBookById(bookId);
               mLiveBook.postValue(book);
            }
        });
    }

    public void saveBook(String bookTitle, String bookAuthor, String bookRating, String bookCategory) {
        Book book = mLiveBook.getValue();
        Double rating = null;
        try{
            rating = Double.parseDouble(bookRating);
        }catch (Exception ex){
            Toast toast =Toast.makeText(super.getApplication().getApplicationContext(),NOT_VALID_RATING,Toast.LENGTH_SHORT);
            toast.show();
        }
        if(rating !=null) {
            if (book == null) {
                if (TextUtils.isEmpty(bookTitle.trim())|| TextUtils.isEmpty(bookTitle.trim())) {
                    return;
                }
                book = new Book(bookTitle.trim(), bookAuthor.trim(), rating, bookCategory.trim(),null);
            } else {
                book.setTitle(bookTitle.trim());
                book.setAuthor(bookAuthor.trim());
                book.setRating(rating);
                book.setBookCategory(bookCategory.trim());
            }
            if(mLiveBook.getValue()==null) {
                book.setServerId(null);
                mainService.insertBook(book);
            }
            else
                mainService.updateBook(book);
        }
    }

    public void deleteBook() {
        mainService.deleteBook(mLiveBook.getValue());
    }
}
