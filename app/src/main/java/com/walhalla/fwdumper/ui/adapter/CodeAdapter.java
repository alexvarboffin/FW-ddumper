package com.walhalla.fwdumper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walhalla.fwdumper.domen.Code;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.PlanetViewHolder> {

    public interface Callback {
        void onClick(Code code, int position);
    }

    private List<Code> data;
    private final Callback mCallback;

    public CodeAdapter(List<Code> list, Context context, Callback mCallback) {
        this.data = list;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public CodeAdapter.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new PlanetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeAdapter.PlanetViewHolder holder, int position) {
        Code code = data.get(position);
        holder.bind(code, mCallback);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text2;
        protected TextView text1;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.image_id);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }

        public void bind(Code code, Callback mCallback) {
            //holder.image.setImageResource(R.drawable.planetimage);
            text1.setText(code.getCode());
            text2.setText(code.getDescription());

            itemView.setOnClickListener(v -> {
                mCallback.onClick(code, getAdapterPosition());
            });
        }
    }
}

