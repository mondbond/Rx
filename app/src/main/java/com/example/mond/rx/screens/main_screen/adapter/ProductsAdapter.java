package com.example.mond.rx.screens.main_screen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mond.rx.R;
import com.example.mond.rx.models.products.Result;
import com.example.mond.rx.models.simpl_models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mond on 14.07.17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Product> mProductList;

    public ProductsAdapter (List<Product> product) {
        mProductList = product;
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mProductList != null) {
            return mProductList.size();
        } else {
            return 0;
        }
    }

    public void setNewProduct(List<Product> products) {
        mProductList = products;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name)
        TextView productName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Product products ) {
            productName.setText(products.getName());
        }
    }
}
