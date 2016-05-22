package com.alexzh.simplecoffeeorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {
    public interface CoffeeOrderListener {
        void onCoffeeChanged(Coffee coffee, CoffeeCountPicker.CoffeeOrderOperation operation, int count);
    }

    private int[] countArray;
    private List<Coffee> mCoffeeList;
    private CoffeeOrderListener mListener;
    private Context mContext;

    public CoffeeAdapter(Context context, HashMap<Coffee, Integer> coffeeOrderMap, CoffeeOrderListener listener) {

        this.mContext = context;
        mListener = listener;
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

    public void setCoffeeList(HashMap<Coffee, Integer> coffeeOrderMap) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_coffee, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mCoffeeName.setText(mCoffeeList.get(position).getName());
        holder.mCoffeePrice.setText(mContext.getString(R.string.price,
                mCoffeeList.get(position).getPrice()));

        holder.mCoffeeCountPicker.setOnCoffeeCountPickerListener(new CoffeeCountPicker.OnCoffeeCountPickerListener() {
            @Override
            public void onPickerChanged(int coffeeCount, CoffeeCountPicker.CoffeeOrderOperation operation) {
                Log.d("onPickerChanged", "count = "+coffeeCount + "; operation = "+operation);
                if (mListener != null && mCoffeeList != null) {
                    mListener.onCoffeeChanged(mCoffeeList.get(position), operation, holder.mCoffeeCountPicker.getCoffeeCount());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCoffeeName;
        private TextView mCoffeePrice;
        private CoffeeCountPicker mCoffeeCountPicker;

        public ViewHolder(View itemView) {
            super(itemView);

            mCoffeeName = (TextView) itemView.findViewById(R.id.coffee_name);
            mCoffeePrice = (TextView) itemView.findViewById(R.id.coffee_price);
            mCoffeeCountPicker = (CoffeeCountPicker) itemView.findViewById(R.id.coffee_count_picker);

            itemView.setTag(itemView);
        }
    }
}
