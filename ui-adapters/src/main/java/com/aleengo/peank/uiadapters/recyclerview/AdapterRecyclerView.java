package com.aleengo.peank.uiadapters.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aleengo.peank.uiadapters.ItemView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * Copyright Aleengo 2019. All rights reserved.
 * Created by bau.cj on 22/06/2019.
 */
public abstract class AdapterRecyclerView<E, ITEMVIEW extends ItemView<E>>
        extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>
        implements AdapterRecylerViewBase<E> {

    @Getter
    private Context context;
    @Getter
    private final List<E> items;
    private final List<E> mOriginalList;

    public AdapterRecyclerView(Context context) {
        this(context, new LinkedList<E>());
    }

    public AdapterRecyclerView(Context context, List<E> items) {
        this.context = context;
        this.items = items;
        mOriginalList = items;
    }

    public abstract ITEMVIEW onCreateItemView(ViewGroup parent, int viewType);

    @Override
    public List<E> getOriginalList() {
        return new LinkedList<>(mOriginalList);
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ITEMVIEW itemview = onCreateItemView(parent, viewType);
        return new ViewHolder(itemview.get(), itemview);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public E getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemPosition(final E item) {
        final int count = getItemCount();

        for (int pos = 0; pos < count; pos++) {
            final E e = items.get(pos);
            if (!e.equals(item)) continue;
            return pos;
        }
        return NO_POSITION;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void clear() {
        final int count = getItemCount();
        items.clear();
        notifyItemRangeChanged(0, count);
    }

    @Override
    public void addItem(E item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void addItem(int position, E item) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void updateItem(int position, E item) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    @Override
    public void addItems(Collection<E> collection) {
        addItems(collection, false);
    }

    @Override
    public void addItems(Collection<E> newItems, boolean extend) {
        int count = getItemCount();

        if (count == 0) {
            mOriginalList.addAll(newItems);
        }

        if (!extend) {
            items.clear();
            count = 0;
        }
        items.addAll(count, newItems);
        notifyItemRangeChanged(count, newItems.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Getter
        private ItemView itemView;

        public ViewHolder(@NonNull View view, @NonNull ItemView itemView) {
            super(view);
            this.itemView = itemView;
        }
    }
}
