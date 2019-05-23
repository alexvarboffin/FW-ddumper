package com.walhalla.fwdumper.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import com.walhalla.boilerplate.domain.executor.impl.BackgroundExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;
import com.walhalla.common.ui.adapter.GalleryAdapter;
import com.walhalla.data.Data;
import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.Application;
import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;
import com.walhalla.fwdumper.databinding.FragmentControlBinding;
import com.walhalla.fwdumper.ui.adapter.PaddingDecore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import nl.walhalla.domain.interactors.AdvertInteractor;
import nl.walhalla.domain.interactors.impl.AdvertInteractorImpl;

public class ControlPanel extends Fragment implements GalleryAdapter.Presenter {


    private static final String TAG = "@@@";
    private List<ShellCommand> mShellCommandList;
    private GalleryAdapter mGalleryAdapter;
    private AdView mAdView;

    public ControlPanel() {
    }


    private FragmentControlBinding mBinding;


    public static ControlPanel newInstance() {
        return new ControlPanel();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mShellCommandList = new ArrayList<>();
        mShellCommandList.add(new ShellCommand("reboot", getString(R.string.shell_reboot), R.drawable.ic_reboot,
                android.R.color.holo_red_dark) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });
        mShellCommandList.add(new ShellCommand("reboot recovery", getString(R.string.shell_reboot_to_recovery), R.drawable.ic_android_black_24dp,
                android.R.color.holo_blue_dark) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });
        mShellCommandList.add(new ShellCommand("reboot bootloader", getString(R.string.shell_reboot_to_bootloader),
                R.drawable.ic_security, android.R.color.holo_green_dark) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });

        mShellCommandList.add(new ShellCommand(null, "", -1, R.color.colorPrimary) {

            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });

        if (mGalleryAdapter == null) {
            mGalleryAdapter = new GalleryAdapter(getContext(), mShellCommandList);
        }
        mGalleryAdapter.setPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        mBinding.recyclerView.setLayoutParams(params);
//        mView = mBinding.recyclerView;

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_control, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int columns = getResources().getInteger(R.integer.gallery_columns);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), columns);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.addItemDecoration(new PaddingDecore(8, columns));
        mBinding.recyclerView.setAdapter(mGalleryAdapter);



        Application application = (Application) getActivity().getApplication();
//        Handler handler = new Handler();
        AdvertInteractorImpl interactor = new AdvertInteractorImpl(
                BackgroundExecutor.getInstance(),
                MainThreadImpl.getInstance(), application.getmAdvertRepository());
        interactor.selectView(mBinding.bottomContainer, new AdvertInteractor.Callback<View>() {
            @Override
            public void onMessageRetrieved(int id, View message) {
                try {
                    //viewGroup.removeView(message);
                    if (message.getParent() != null) {
                        ((ViewGroup) message.getParent()).removeView(message);
                    }
                    mBinding.bottomContainer.addView(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRetrievalFailed(String error) {
                Log.i(TAG, "onRetrievalFailed: ");
            }
        });


//        mAdView = AdMobCase.createBanner(getContext(), "ca-app-pub-5111357348858303/5329802512");
//        mAdView.setAdListener(new AdListener(mAdView));
//        mAdView.loadAd(AdMobCase.buildAdRequest());
//        mBinding.bottomContainer.addView(mAdView);
    }

    @Override
    public void exec(String command) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.executeTheCommand(new Data.BaseCommand(command), true);
        }
    }
}
