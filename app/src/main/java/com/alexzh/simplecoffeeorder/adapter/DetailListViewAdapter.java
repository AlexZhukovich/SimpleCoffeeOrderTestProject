package com.alexzh.simplecoffeeorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.model.Coffee;
import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.utils.CoffeeOrderUtils;

import java.util.HashMap;

public class DetailListViewAdapter extends BaseAdapter {
    private HashMap<Coffee, Integer> mOrderedCoffee;
    private Context mContext;

    public DetailListViewAdapter(Context context, HashMap<Coffee, Integer> orderedCoffee) {
        this.mOrderedCoffee = orderedCoffee;
        this.mContext = context;
    }

    public void setOrderedCoffee(HashMap<Coffee, Integer> orderedCoffee) {
        this.mOrderedCoffee = orderedCoffee;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mOrderedCoffee == null ? 0 : mOrderedCoffee.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_coffee_detail, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mCoffeeName = (TextView) convertView.findViewById(R.id.coffee_name);
            viewHolder.mCoffeeCount = (TextView) convertView.findViewById(R.id.coffee_count);
            viewHolder.mCoffeePrice = (TextView) convertView.findViewById(R.id.coffee_price);
            viewHolder.mTotalPrice = (TextView) convertView.findViewById(R.id.total_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position <= mOrderedCoffee.size()) {
            Coffee coffee = CoffeeOrderUtils.getCoffeeByPosition(mOrderedCoffee, position);
            if (coffee != null) {
                int count = CoffeeOrderUtils.getCountByCoffee(mOrderedCoffee, coffee);

                viewHolder.mCoffeeName.setText(coffee.getName());
                viewHolder.mCoffeeCount.setText(String.valueOf(count));
                viewHolder.mCoffeePrice.setText(mContext.getString(R.string.price, coffee.getPrice()));
                viewHolder.mTotalPrice.setText(mContext.getString(R.string.price, (float)(count * coffee.getPrice())));
            }
        }
        return convertView;
    }

    static class ViewHolder {
        TextView mCoffeeName;
        TextView mCoffeeCount;
        TextView mCoffeePrice;
        TextView mTotalPrice;
    }
}
