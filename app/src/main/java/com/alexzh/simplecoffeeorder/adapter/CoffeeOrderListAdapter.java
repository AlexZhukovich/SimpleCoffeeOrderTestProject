package com.alexzh.simplecoffeeorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.customview.CoffeeCountPicker;
import com.alexzh.simplecoffeeorder.model.Coffee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class CoffeeOrderListAdapter extends RecyclerView.Adapter<CoffeeOrderListAdapter.CoffeeViewHolder> {
    public interface CoffeeOrderListener {
        void onCoffeeChanged(Coffee coffee, CoffeeCountPicker.CoffeeOrderOperation operation, int count);
    }

    private int[] countArray;
    private List<Coffee> mCoffeeList;
    private CoffeeOrderListener mCoffeeOrderListener;
    private Context mContext;
    private View.OnClickListener mClickListener;

    public CoffeeOrderListAdapter(Context context, HashMap<Coffee, Integer> coffeeOrderMap) {

        this.mContext = context;
        mCoffeeList = new ArrayList<>();
        countArray = new int[coffeeOrderMap == null ? 0 : coffeeOrderMap.size()];
        int position = 0;
        if (coffeeOrderMap != null && coffeeOrderMap.size() > 0) {
            for (Coffee coffee : coffeeOrderMap.keySet()) {
                mCoffeeList.add(coffee);
                countArray[position] = coffeeOrderMap.get(coffee);
                position++;
            }
        }
    }

    public void setCoffeeOrderListener(CoffeeOrderListener listener) {
        this.mCoffeeOrderListener = listener;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.mClickListener = listener;
    }

    public void setCoffeeList(TreeMap<Coffee, Integer> coffeeOrderMap) {
        mCoffeeList = new ArrayList<>();
        countArray = new int[coffeeOrderMap == null ? 0 : coffeeOrderMap.size()];
        int position = 0;
        if (coffeeOrderMap != null && coffeeOrderMap.size() > 0) {
            for (Coffee coffee : coffeeOrderMap.keySet()) {
                mCoffeeList.add(coffee);
                countArray[position] = coffeeOrderMap.get(coffee);
                position++;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public CoffeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_coffee, parent, false);
        return new CoffeeViewHolder(itemView, mClickListener);
    }

    @Override
    public void onBindViewHolder(final CoffeeViewHolder holder, final int position) {
        holder.mCoffeeName.setText(mCoffeeList.get(position).getName());
        holder.mCoffeePrice.setText(mContext.getString(R.string.price,
                mCoffeeList.get(position).getPrice()));

        holder.mCoffeeCountPicker.setOnCoffeeCountPickerListener(new CoffeeCountPicker.OnCoffeeCountPickerListener() {
            @Override
            public void onPickerChanged(int coffeeCount, CoffeeCountPicker.CoffeeOrderOperation operation) {
                if (mCoffeeOrderListener != null && mCoffeeList != null) {
                    mCoffeeOrderListener.onCoffeeChanged(mCoffeeList.get(position), operation, holder.mCoffeeCountPicker.getCoffeeCount());
                }
                countArray[position] = coffeeCount;
            }
        });

        if (countArray.length > position) {
            holder.mCoffeeCountPicker.setCoffeeCount(countArray[position]);
        }
    }

    @Override
    public int getItemCount() {
        return mCoffeeList == null ? 0 : mCoffeeList.size();
    }

    public static class CoffeeViewHolder extends RecyclerView.ViewHolder {
        public TextView mCoffeeName;
        public TextView mCoffeePrice;
        public CoffeeCountPicker mCoffeeCountPicker;

        public CoffeeViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);

            mCoffeeName = (TextView) itemView.findViewById(R.id.coffee_name);
            mCoffeePrice = (TextView) itemView.findViewById(R.id.coffee_price);
            mCoffeeCountPicker = (CoffeeCountPicker) itemView.findViewById(R.id.coffee_count_picker);
            if (listener != null) {
                itemView.setOnClickListener(listener);
            }

            itemView.setTag(itemView);
        }
    }
}
