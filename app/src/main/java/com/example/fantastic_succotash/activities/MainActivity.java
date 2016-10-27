package com.example.fantastic_succotash.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fantastic_succotash.MockyService;
import com.example.fantastic_succotash.R;
import com.example.fantastic_succotash.adapters.NewsAdapter;
import com.example.fantastic_succotash.data.News;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static String VIEW_STATE_SAVED = "grid_or_list";

    private RecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private MockyService mockyService;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private boolean gridLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
            gridLayout = savedInstanceState.getBoolean(VIEW_STATE_SAVED);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mockyService = retrofit.create(MockyService.class);

        refresh();

        int orientation = getResources().getConfiguration().orientation;

        newsAdapter = new NewsAdapter();

        gridLayoutManager = new GridLayoutManager(this, orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(gridLayout ? gridLayoutManager : linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setGridLayout(gridLayout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(VIEW_STATE_SAVED, gridLayout);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem menuGrid = menu.findItem(R.id.menu_grid);
        menuGrid.setVisible(!gridLayout);

        MenuItem menuList = menu.findItem(R.id.menu_list);
        menuList.setVisible(gridLayout);

        return true;
    }

    private void refresh() {
        mockyService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<News[]>() {
                    @Override
                    public void onSuccess(News[] value) {
                        newsAdapter.setData(value);
                        newsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable error) { }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_grid:
                gridLayout = true;
                recyclerView.setLayoutManager(gridLayoutManager);
                newsAdapter.setGridLayout(gridLayout);
                invalidateOptionsMenu();
                return true;
            case R.id.menu_list:
                gridLayout = false;
                recyclerView.setLayoutManager(linearLayoutManager);
                newsAdapter.setGridLayout(gridLayout);
                invalidateOptionsMenu();
                return true;
            case R.id.menu_refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
