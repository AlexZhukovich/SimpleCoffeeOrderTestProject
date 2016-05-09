package com.alexzh.simplecoffeeorder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CoffeeCountPicker extends FrameLayout implements View.OnClickListener {
    public interface OnCoffeeCountPickerListener {
        void onPickerChanged(int coffeeCount);
    }

    private OnCoffeeCountPickerListener mPickerListener;
    private int mCoffeeCount;
    private TextView mCoffeeCountLabel;

    public CoffeeCountPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public CoffeeCountPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CoffeeCountPicker(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.view_coffee_count_picker, null);
        mCoffeeCountLabel = (TextView) view.findViewById(R.id.coffee_count);
        view.findViewById(R.id.coffee_increment).setOnClickListener(this);
        view.findViewById(R.id.coffee_decrement).setOnClickListener(this);

        addView(view);
    }

    public int getCoffeeCount() {
        return mCoffeeCount;
    }

    public void setCoffeeCount(int coffeeCount) {
        this.mCoffeeCount = coffeeCount;
        displayCoffeeCount();
        notifyListener();
    }

    public void setOnCoffeeCountPickerListener(OnCoffeeCountPickerListener listener) {
        mPickerListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coffee_increment:
                mCoffeeCount++;
                notifyListener();
                break;
            case R.id.coffee_decrement:
                if (mCoffeeCount > 0) {
                    mCoffeeCount--;
                    notifyListener();
                }
                break;
        }
        displayCoffeeCount();
    }

    private void notifyListener() {
        if (mPickerListener != null) {
            mPickerListener.onPickerChanged(mCoffeeCount);
        }
    }

    private void displayCoffeeCount() {
        mCoffeeCountLabel.setText(String.valueOf(mCoffeeCount));
    }
}
