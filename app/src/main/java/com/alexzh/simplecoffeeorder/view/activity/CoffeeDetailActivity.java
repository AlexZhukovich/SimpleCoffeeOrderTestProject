package com.alexzh.simplecoffeeorder.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.adapter.CoffeeDetailAdapter;
import com.alexzh.simplecoffeeorder.customview.recyclerview.DividerItemDecoration;
import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.presentation.CoffeeDetailPresenter;
import com.alexzh.simplecoffeeorder.presentation.CoffeeDetailPresenterImpl;
import com.alexzh.simplecoffeeorder.view.CoffeeDetailView;

public class CoffeeDetailActivity extends AppCompatActivity implements CoffeeDetailView {
    private final static String COFFEE = "coffee";

    private Toolbar mToolbar;
    private CoffeeDetailAdapter mAdapter;

    private CoffeeDetailPresenter mPresenter;

    public static Intent createCoffeeDetailIntent(Context context, Coffee coffee) {
        Intent intent = new Intent(context, CoffeeDetailActivity.class);
        intent.putExtra(COFFEE, coffee);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);

        mPresenter = new CoffeeDetailPresenterImpl();

        mToolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(mToolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Drawable divider = ContextCompat.getDrawable(this, R.drawable.coffee_item_divider);
            recyclerView.addItemDecoration(new DividerItemDecoration(divider));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new CoffeeDetailAdapter(getApplicationContext(), null);
            recyclerView.setAdapter(mAdapter);
        }
        mPresenter.setView(this);
    }

    @Override
    public Coffee getCoffee() {
        if (getIntent().getExtras() != null) {
            return (Coffee) getIntent().getSerializableExtra(COFFEE);
        }
        return null;
    }

    @Override
    public void displayCoffeeName(String coffeeName) {
        getSupportActionBar().setTitle(coffeeName);
    }

    @Override
    public void displayCoffeeDetailInformation(Coffee coffee) {
        mAdapter.setCoffee(coffee);
    }
}
