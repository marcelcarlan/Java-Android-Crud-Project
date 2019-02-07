package com.carlanmarcel.books.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.service.MainService;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Book>> mBooks;
    private MainService mainService;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainService = MainService.getInstance(application.getApplicationContext());
        mBooks = mainService.getAllBooks();
    }

    public void deleteAllBooks() {
        mainService.deleteAllBooks();
    }


    public void reloadData() {
        mainService.reloadData();
    }
}
