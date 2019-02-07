package com.carlanmarcel.books;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.carlanmarcel.books.model.Book;
import com.carlanmarcel.books.service.ServiceIntent;
import com.carlanmarcel.books.ui.BooksAdapter;
import com.carlanmarcel.books.viewModel.MainViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<Book> bookData = new ArrayList<>();
    private BooksAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<Book>> booksObserver = new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                bookData.clear();
                bookData.addAll(books);

                if(mAdapter == null) {
                    mAdapter = new BooksAdapter(bookData, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mViewModel.mBooks.observe(this,booksObserver);
    }


    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext(),layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(divider);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutAction();
            return true;
        } else if (id == R.id.action_delete_all){
            deleteAllNotes();
            return true;
        } else if (id == R.id.action_reload_data){
            reloadData();
            return true;
        } else if(id == R.id.action_statistics){
            Intent myIntent = new Intent(this,StatisticsActivity.class);
            startActivity(myIntent);
            return true;
        } else if(id == R.id.action_nearby){
            Intent myIntent = new Intent(this, MapsActivity.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void reloadData(){
        mViewModel.reloadData();
    }

    private void logoutAction() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllBooks();
    }




}
