package com.example.mond.rx.screens.main_screen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mond.rx.R;
import com.example.mond.rx.domain.models.Store;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> mStoreList;

    public StoreAdapter(List<Store> stores) {
        mStoreList = stores;
    }

    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mStoreList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mStoreList != null) {
            return mStoreList.size();
        } else {
            return 0;
        }
    }

    public void setNewStores(List<Store> products) {
        mStoreList = products;
        notifyDataSetChanged();
    }

    public void clear() {
        mStoreList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.store_name)
        TextView storeName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Store store) {
            storeName.setText(store.getName());
        }
    }
}
