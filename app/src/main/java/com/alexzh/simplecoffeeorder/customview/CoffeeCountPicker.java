package com.alexzh.simplecoffeeorder.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.R;

public class CoffeeCountPicker extends FrameLayout implements View.OnClickListener {
    public interface OnCoffeeCountPickerListener {
        void onPickerChanged(int coffeeCount, CoffeeOrderOperation operation);
    }

    public enum CoffeeOrderOperation {
        INIT, ADDED, REMOVED
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
        notifyListener(CoffeeOrderOperation.INIT);
    }

    public void setOnCoffeeCountPickerListener(OnCoffeeCountPickerListener listener) {
        mPickerListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coffee_increment:
                mCoffeeCount++;
                notifyListener(CoffeeOrderOperation.ADDED);
                break;
            case R.id.coffee_decrement:
                if (mCoffeeCount > 0) {
                    mCoffeeCount--;
                    notifyListener(CoffeeOrderOperation.REMOVED);
                }
                break;
        }
        displayCoffeeCount();
    }

    private void notifyListener(CoffeeOrderOperation operation) {
        if (mPickerListener != null) {
            mPickerListener.onPickerChanged(mCoffeeCount, operation);
        }
    }

    private void displayCoffeeCount() {
        mCoffeeCountLabel.setText(String.valueOf(mCoffeeCount));
    }
}
