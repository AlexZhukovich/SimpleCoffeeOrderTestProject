package com.alexzh.simplecoffeeorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {
    public interface CoffeeOrderListener {
        void onCoffeeChanged(Coffee coffee, CoffeeCountPicker.CoffeeOrderOperation operation, int count);
    }

    private List<Coffee> mCoffeeList;
    private CoffeeOrderListener mListener;
    private Context mContext;

    public CoffeeAdapter(Context context, List<Coffee> coffeeList, CoffeeOrderListener listener) {
        this.mCoffeeList = coffeeList;
        this.mContext = context;
        mListener = listener;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.mCoffeeList = coffeeList;
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
                if (mListener != null && mCoffeeList != null) {
                    mListener.onCoffeeChanged(mCoffeeList.get(position), operation, holder.mCoffeeCountPicker.getCoffeeCount());
                }
            }
        });
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
