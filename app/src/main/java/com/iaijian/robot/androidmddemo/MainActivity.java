package com.iaijian.robot.androidmddemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits = {
            new Fruit("Apple",R.drawable.apple),new Fruit("Banana",R.drawable.banana),
            new Fruit("Orange",R.drawable.orange),new Fruit("Watermelon",R.drawable.watermelon),
            new Fruit("Pear",R.drawable.pear),new Fruit("Grape",R.drawable.grape),
            new Fruit("Pineapple",R.drawable.pineapple),new Fruit("Strawberry",R.drawable.straberry),
            new Fruit("Cherry",R.drawable.cherry),new Fruit("Mango",R.drawable.mango)
    };

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static void startInstance(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_draw_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationView.setCheckedItem(item.getItemId());
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"FAB Clicked",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"FAB restored",Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
//        设置下拉刷新进度条的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
//                        隐藏刷新进度条
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i <50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this,"Backup clicked",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.delete:
                Toast.makeText(this,"Delete clicked",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.settings:
                Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT)
                        .show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
