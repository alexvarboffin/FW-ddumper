package com.walhalla.fwdumper.ui.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walhalla.fwdumper.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestersDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Testers");
        builder.setIcon(R.drawable.ic_testers);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_tester_dialog, null, false);
        builder.setView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PlanetAdapter adapter = new PlanetAdapter(data(), getContext());
        recyclerView.setAdapter(adapter);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }

    private List<String> data() {
        List<String> data = new ArrayList<>();
        data.add("Lulz Ceh");
        data.add("alexvarboffin");
        data.add("@neurox");
        data.add("Xorry");
        data.add("David Huber");
        return data;
    }


    public static class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {

        List<String> data;

        PlanetAdapter(List<String> planetList, Context context) {
            this.data = planetList;
        }

        @NonNull
        @Override
        public PlanetAdapter.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new PlanetViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PlanetAdapter.PlanetViewHolder holder, int position) {
            //holder.image.setImageResource(R.drawable.planetimage);
            holder.text.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class PlanetViewHolder extends RecyclerView.ViewHolder {

            //protected ImageView image;
            protected TextView text;

            public PlanetViewHolder(View itemView) {
                super(itemView);
                //image = (ImageView) itemView.findViewById(R.id.image_id);
                text = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
