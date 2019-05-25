package com.walhalla.fwdumper.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walhalla.common.FileUtil;
import com.walhalla.data.Data;
import com.walhalla.domen.FWProc;
import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.LsObj;
import com.walhalla.domen.item.MyObj;
import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by combo on 25.03.2017.
 */

public class ComplexAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_BLOCK = 0;
    private final int TYPE_BLOCK_LS = 1;

    private final int TYPE_PROC = 2;
    private final int TYPE_TEXT = -1;

    private int[] colorzz = {0, 0};

    //private final View.OnClickListener listener;
    private ChildItemClickListener childItemClickListener;
    // The items to display in your RecyclerView
    private List<Obj> items;

//    private Drawable bin;
//    private Drawable img;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexAdapter(List<Obj> items) {
        this.items = items;
        //this.listener = listener;
    }

    public ComplexAdapter(Context context) {
        items = new ArrayList<>();

//        bin = context.getResources().getDrawable(R.mipmap.ic_launcher);
//        bin.setBounds(0, 0, 32, 32);
//
//        img = context.getResources().getDrawable(R.drawable.ic_block_img);
//        img.setBounds(0, 0, 32, 32);


        colorzz[0] = context.getResources().getColor(R.color.colorPrimary);
        colorzz[1] = context.getResources().getColor(android.R.color.holo_orange_dark);
    }

    public void setChildItemClickListener(ChildItemClickListener listener) {
        childItemClickListener = listener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof LsObj) {
            return TYPE_BLOCK_LS;
        } else if (items.get(position) instanceof MyObj) {
            return TYPE_BLOCK;
        } else if (items.get(position) instanceof FWProc) {
            return TYPE_PROC;
        }
        return TYPE_TEXT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {

            case TYPE_BLOCK:
                viewHolder = new ViewHolderDevBlock(
                        inflater.inflate(R.layout.item_dev_block, viewGroup, false), childItemClickListener);
                break;

            case TYPE_BLOCK_LS:
                viewHolder = new ViewHolderLsBlock(
                        inflater.inflate(R.layout.item_dev_block_ls, viewGroup, false), childItemClickListener);
                break;

            case TYPE_TEXT:
                View view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(view);
                break;

            case TYPE_PROC:
                view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(view);
                break;

            default:
                view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        switch (viewHolder.getItemViewType()) {
            case TYPE_BLOCK:
                ViewHolderDevBlock vh1 = (ViewHolderDevBlock) viewHolder;
                configureDevBlockViewHolder(vh1, position);
                break;

            case TYPE_BLOCK_LS:
                ViewHolderLsBlock vh7 = (ViewHolderLsBlock) viewHolder;
                configureLsViewHolder(vh7, position);
                break;

            case TYPE_TEXT:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
            case TYPE_PROC:
                vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureProcViewHolder(vh, position);
                break;

            default:
                vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureProcViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {

        final FWProc fwProc = (FWProc) items.get(position);

        vh.getLabel().setText("" + fwProc.getCommand());
        vh.getLabel().setOnClickListener(v ->
                childItemClickListener.requestExtendedInfo(fwProc.getCommand()));
    }


    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        Obj o = items.get(position);
        vh.getLabel().setText(o.toString());
    }


    private void configureLsViewHolder(ViewHolderLsBlock holder, int position) {
        LsObj operation = (LsObj) items.get(position);

        if (operation != null) {


            //Fix filename
            String fileName = FileUtil.fixName(operation);


            holder.name.setText(fileName);
            if (operation.isDirectory()) {
//                holder.name.setCompoundDrawables(bin, null, null, null);
                holder.dot.setBackgroundColor(colorzz[0]);
            } else {
//                holder.name.setCompoundDrawables(img, null, null, null);
                holder.dot.setBackgroundColor(colorzz[1]);
            }
            holder.location.setText(operation.getLocation());
            holder.info.setText(operation.getInfo());
            holder.id.setText("" + (position + 1));
        }
    }


    private void configureDevBlockViewHolder(ViewHolderDevBlock holder, int position) {
        MyObj operation = (MyObj) items.get(position);
        if (operation != null) {


            String name = FileUtil.fixName(operation);

            holder.name.setText(name);
            if (operation.isDirectory()) {
//                holder.name.setCompoundDrawables(bin, null, null, null);
                holder.dot.setBackgroundColor(colorzz[0]);
            } else {
//                holder.name.setCompoundDrawables(img, null, null, null);
                holder.dot.setBackgroundColor(colorzz[1]);
            }
            holder.location.setText(operation.getLocation());
        }
    }

    public void swap(List<Obj> data) {
        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void swap(Obj data) {
        items.clear();
        items.add(data);
        this.notifyDataSetChanged();
    }


    //========================================================================================
    // ERR_ROW
    //========================================================================================

    private static class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

        private TextView mLabel;

        private RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
            mLabel = itemView.findViewById(android.R.id.text1);
        }

        TextView getLabel() {
            return mLabel;
        }
    }

    //================================================================
    //  ROW
    //================================================================

    public class ViewHolderDevBlock extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ChildItemClickListener listener;

        private View dot;
        private TextView name;
        private TextView location;


        private ViewHolderDevBlock(View view, ChildItemClickListener listener) {
            super(view);
            view.setOnClickListener(this);
            this.listener = listener;
            name = view.findViewById(R.id.tv_block_name);
            location = view.findViewById(R.id.tv_block_location);
            dot = view.findViewById(R.id.dot);

            view.findViewById(R.id.btn_create_dump_to_sd).setOnClickListener(this);
            view.findViewById(R.id.btn_create_dump_to_adb).setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {


            MyObj blockObj = (MyObj) items.get(getAdapterPosition());

            if (listener != null) {

                switch (view.getId()) {
                    case R.id.btn_create_dump_to_sd:
                        listener.createDumpSdCardRequest(blockObj.getLocation(), blockObj.getName());
                        break;

                    case R.id.btn_create_dump_to_adb:
                        listener.createDumpAdbRequest(blockObj.getLocation(), blockObj.getName());
                        break;


                    default:
                        if (blockObj.isDirectory()) {
                            childItemClickListener.executeTheCommand(
                                    new Data.LaCommand(blockObj.getLocation()), true);
                        }
                        break;
                }
            }
        }
    }


    public class ViewHolderLsBlock extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ChildItemClickListener listener;

        private View dot;
        private TextView name;
        private TextView location;
        private TextView info;
        private TextView id;

        private ViewHolderLsBlock(View view, ChildItemClickListener listener) {
            super(view);
            view.setOnClickListener(this);
            this.listener = listener;
            name = view.findViewById(R.id.tv_block_name);
            location = view.findViewById(R.id.tv_block_location);
            dot = view.findViewById(R.id.dot);
            info = view.findViewById(R.id.info);
            id = view.findViewById(R.id.id);
            view.findViewById(R.id.btn_create_dump_to_sd).setOnClickListener(this);
            view.findViewById(R.id.btn_create_dump_to_adb).setOnClickListener(this);
            view.setOnClickListener(this);
        }

        /**
         * RecyclerView items listener
         */


        @Override
        public void onClick(View view) {
            if (listener != null) {

                MyObj blockObj = (MyObj) items.get(getAdapterPosition());
                switch (view.getId()) {
                    case R.id.btn_create_dump_to_sd:
                        listener.createDumpSdCardRequest(blockObj.getLocation(), blockObj.getName());
                        break;

                    case R.id.btn_create_dump_to_adb:
                        listener.createDumpAdbRequest(blockObj.getLocation(), blockObj.getName());
                        break;


                    default:
                        if (blockObj.isDirectory()) {
                            childItemClickListener.executeTheCommand(
                                    new Data.LaCommand(blockObj.getLocation()), true);
                        }
                        break;
                }
            }
        }
    }


    public interface ChildItemClickListener {
        void executeTheCommand(ShellCommand shellCommand, boolean b);

        void createDumpSdCardRequest(String location, String output_file_name);

        void requestExtendedInfo(String command);

        void createDumpAdbRequest(String location, String name);
    }
}