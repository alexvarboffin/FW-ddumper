package com.walhalla.common.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private BaseViewHolder(View itemView) {
        super(itemView);
    }

    public interface ViewHolderCallback {
        void onItemClicked(View view, int lessonPosition);
    }
}