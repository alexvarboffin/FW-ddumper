package com.walhalla.common.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.walhalla.common.ui.BaseViewHolder;
import com.walhalla.common.ui.helper.SquareLayout;
import com.walhalla.domen.ShellCommand;
import com.walhalla.fwdumper.R;

import java.util.List;

/**
 * Created by combo on 18.09.2017.
 */

public class GalleryAdapter
        extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder>
        implements BaseViewHolder.ViewHolderCallback {


    public interface Presenter {

        void exec(String command);
    }


    private final Context mContext;
    private List<ShellCommand> mShellCommandList;


    @Override
    public void onItemClicked(View view, int position) {
        String command = mShellCommandList.get(position).getCommand();
        if (command != null) {
            mPresenter.exec(command);
        }
    }


    /**
     * Event Callback
     *
     * @param presenter
     */

    private Presenter mPresenter;

    public void setPresenter(@NonNull Presenter presenter) {
        this.mPresenter = presenter;
    }


    public GalleryAdapter(Context context, List<ShellCommand> signs) {
        mContext = context;
        this.mShellCommandList = signs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);
        return new MyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShellCommand model = mShellCommandList.get(position);
        int resDrawable = model.getIcon();

        if (model.getName() == null || model.getName().isEmpty()) {
            holder.name.setVisibility(View.GONE);
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(model.getName());
        }

        try {
//            Glide.with(mContext)
//                    .load(Uri.parse("file:///android_asset/" + resDrawable))
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .error(R.mipmap.ic_launcher)
//                    //.centerCrop()
//                    .into(holder.thumbnail);


//            Glide.with(mContext)
//                    .load(Uri.parse(ASSET_SCHEME + resDrawable))
//                    //.error(R.mipmap.ic_launcher)
//                    //.centerCrop()
//                    .into(holder.thumbnail);


            if (resDrawable != -1) {
                Resources res = mContext.getResources();
                Drawable drawable = res.getDrawable(resDrawable);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    VectorDrawable vectorDrawable = (VectorDrawable) drawable;
                    holder.thumbnail.setImageDrawable(vectorDrawable);
                } else {

                    VectorDrawableCompat vectorDrawableCompat = (VectorDrawableCompat) drawable;
                    holder.thumbnail.setImageDrawable(vectorDrawableCompat);

//                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//                    holder.thumbnail.setImageDrawable(bitmapDrawable);
                }
            }


            int color = ContextCompat.getColor(mContext, model.getColor());
            holder.container.setBackgroundColor(color);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mShellCommandList.size();
    }


    interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    /**
     * Recycler item view
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        SquareLayout container;
        TextView name;

        private MyViewHolder(View view, BaseViewHolder.ViewHolderCallback callback) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
            container = view.findViewById(R.id.container);
            name = view.findViewById(R.id.title);

            view.setOnClickListener(view1 -> {

                if (callback != null) {
                    callback.onItemClicked(view1, getAdapterPosition());
                }
            });
        }
    }


}
