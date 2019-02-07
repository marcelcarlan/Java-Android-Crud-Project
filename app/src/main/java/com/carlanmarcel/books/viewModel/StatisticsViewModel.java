package com.carlanmarcel.books.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.service.MainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsViewModel extends AndroidViewModel {

    private final MainService mainService;

    public StatisticsViewModel(@NonNull Application application) {
        super(application);
        this.mainService = MainService.getInstance(getApplication());
    }

    public Map<String, Float> getDataSet() {
        List<Book> books =  mainService.getAllBooksSimple();
        Map<String, Float> data  = new HashMap<>();
        for (Book book: books){
            Float per =  data.get(book.getBookCategory());
            data.put(book.getBookCategory(),(per == null) ? 1 : per + 1);
        }
        Double total = (double) books.size();
        for (Map.Entry<String, Float> entry: data.entrySet()) {
            Float value = entry.getValue();
            entry.setValue((float) (value*100/total));
        }
        return data;
    }
}
