package com.alexzh.simplecoffeeorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexzh.simplecoffeeorder.R;
import com.alexzh.simplecoffeeorder.model.Coffee;

public class CoffeeDetailAdapter extends RecyclerView.Adapter<CoffeeDetailAdapter.ViewHolder> {
    private final static int COFFEE_INGREDIENT_POSITION = 0;
    private final static int COFFEE_PRICE_POSITION = 1;

    private Coffee mCoffee;
    private Context mContext;

    public CoffeeDetailAdapter(Context context, Coffee coffee) {
        this.mCoffee = coffee;
        this.mContext = context;
    }

    public void setCoffee(Coffee coffee) {
        this.mCoffee = coffee;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_coffee_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case COFFEE_INGREDIENT_POSITION:
                String ingredients = "";
                for (int i = 0; i < mCoffee.getIngredients().length; i++) {
                    ingredients += mCoffee.getIngredients()[i];
                    if (i != mCoffee.getIngredients().length - 1) {
                        ingredients += ",\n";
                    }
                }

                holder.mItemTitle.setText(R.string.ingredients);
                holder.mItemDescription.setText(ingredients);
                holder.mItemLogo.setImageResource(R.drawable.ic_approve_24dp);
                break;
            case COFFEE_PRICE_POSITION:
                holder.mItemTitle.setText(R.string.price_label);
                holder.mItemDescription.setText(mContext.getString(R.string.price, mCoffee.getPrice()));
                holder.mItemLogo.setImageResource(R.drawable.ic_shopping_cart_24dp);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCoffee != null ? 2 : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTitle;
        private TextView mItemDescription;
        private ImageView mItemLogo;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemTitle = (TextView) itemView.findViewById(R.id.category_title);
            mItemDescription = (TextView) itemView.findViewById(R.id.category_description);
            mItemLogo = (ImageView) itemView.findViewById(R.id.category_logo);
            itemView.setTag(itemView);
        }
    }
}
