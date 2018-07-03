package com.walhalla.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.R;
import com.walhalla.ui.adapter.ComplexAdapter;

import java.util.ArrayList;

public class RecyclerFragment extends Fragment {

    private static final String KEY_DATA_LIST = "key:arr:list:ccc";
    private ArrayList<Obj> mArrayList;

    private ComplexAdapter adapter;
    private RecyclerView mRecyclerView;
    //private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (adapter == null) {
            adapter = new ComplexAdapter(getContext());
        }
        adapter.setChildItemClickListener((ComplexAdapter.ChildItemClickListener) getActivity());

        if (getArguments() != null) {
            mArrayList = getArguments().getParcelableArrayList(KEY_DATA_LIST);
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
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        swipeRefreshLayout.setOnRefreshListener(initInstances());
        mRecyclerView.setAdapter(adapter);
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
        args.putParcelableArrayList(KEY_DATA_LIST, data);
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
////        adapter.swap(err);
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
        adapter.swap(data);
    }
}
