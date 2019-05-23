package com.walhalla.fwdumper.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.R;
import com.walhalla.fwdumper.ui.adapter.ComplexAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerFragment extends Fragment {

    private static final String KEY_DATA_LIST = "key:arr:list:ccc";
    private ArrayList<Obj> mArrayList;

    private ComplexAdapter mAdapter;
    private RecyclerView mRecyclerView;
    //private SwipeRefreshLayout swipeRefreshLayout;


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mAdapter == null) {
            mAdapter = new ComplexAdapter(getContext());
        }
        mAdapter.setChildItemClickListener((ComplexAdapter.ChildItemClickListener) getActivity());

        if (getArguments() != null) {
            mArrayList = (ArrayList<Obj>) getArguments().getSerializable(KEY_DATA_LIST);
            showData(mArrayList);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
//        TextView textView = view.findViewById(R.id.textView);
//        String zz = new String(new char[555]).replace("\0", "#");
//        textView.setText(zz);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        swipeRefreshLayout.setOnRefreshListener(initInstances());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initInstances() {
        //...
        //swipeRefreshLayout.setRefreshing(false);
    }

    private void init(View view) {
        //swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
    }

    public static RecyclerFragment newInstance(ArrayList<Obj> data) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATA_LIST, data);
        fragment.setArguments(args);
        return fragment;
    }


    //=================================
    //From presenter
    //================================

    //    @Override
//    public void showError(String err) {
////        APIError err = new APIError();
////        err.setErrorMsg(errorMsg);
////        mAdapter.swap(err);
////        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
//    }
//
////    @Override
////    public void showError(int err) {
////        showError(getString(err));
////    }
//
//    @Override
    public void showData(ArrayList<Obj> data) {
        mAdapter.swap(data);
    }
}
