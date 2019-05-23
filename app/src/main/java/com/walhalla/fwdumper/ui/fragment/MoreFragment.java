package com.walhalla.fwdumper.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;
import com.walhalla.fwdumper.databinding.FragmentMoreBinding;
import com.walhalla.fwdumper.task.TaskCallback;

import java.util.List;


public class MoreFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private FragmentMoreBinding mBinding;

    private String mParam1;

//    private OnFragmentInteractionListener mListener;

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance(String param1) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new MainActivity.ExecuteTask(new TaskCallback() {
            @Override
            public void onTaskCompleted(List<String> result) {

                for (String s : result) {
                    mBinding.output.append(s);
                }

            }

            @Override
            public void onProgressUpdate(String progress) {

            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onPreExecute() {

            }
        }).execute(new String[]{"cat /proc/" + mParam1});
    }


//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
