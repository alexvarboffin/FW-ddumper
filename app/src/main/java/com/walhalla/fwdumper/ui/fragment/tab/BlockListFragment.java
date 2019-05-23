package com.walhalla.fwdumper.ui.fragment.tab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import nl.walhalla.domain.interactors.AdvertInteractor;
import nl.walhalla.domain.interactors.impl.AdvertInteractorImpl;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;


import com.walhalla.boilerplate.domain.executor.impl.BackgroundExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;
import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.Application;
import com.walhalla.fwdumper.DumperManager;
import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;
import com.walhalla.fwdumper.databinding.FragmentBlocksListBinding;
import com.walhalla.presentation.view.BlockListView;
import com.walhalla.fwdumper.ui.fragment.BaseFragment;
import com.walhalla.fwdumper.ui.fragment.RecyclerFragment;

import java.util.ArrayList;

/**
 * Created by combo on 12/29/2017.
 */

public class BlockListFragment
        extends BaseFragment implements BlockListView
{


    private static final String TAG = "@@@";
    private DumperManager mDumperManager;
    private FragmentBlocksListBinding mBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDumperManager = DumperManager.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.blocklist, menu);

        SubMenu subMenu = menu.getItem(0).getSubMenu();

        int i = 0;
        for (ShellCommand command : mDumperManager.getCommands()) {
            subMenu.add(Menu.NONE, i, Menu.NONE, command.getCommand());
            subMenu.getItem(i).setIcon(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher));
            i++;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mDumperManager.getCommands().size() > id) {
            exec(id);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exec(int id) {
        ShellCommand fwCommand = mDumperManager.getCommands().get(id);
        if (getActivity() != null) {
            ((MainActivity) getActivity()).executeTheCommand(fwCommand, false);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blocks_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        getChildFragmentManager().beginTransaction()
//                .add(R.id.fl_container, null, "@tag");

        exec(0);




        Application application = (Application) getActivity().getApplication();

        //Handler handler = new Handler();
        AdvertInteractorImpl interactor = new AdvertInteractorImpl(BackgroundExecutor.getInstance(
        //        handler
        ),
                MainThreadImpl.getInstance(), application.getmAdvertRepository());
        interactor.selectView(mBinding.bottomContainer02, new AdvertInteractor.Callback<View>() {
            @Override
            public void onMessageRetrieved(int id, View message) {
                try {
                    //viewGroup.removeView(message);
                    if (message.getParent() != null) {
                        ((ViewGroup) message.getParent()).removeView(message);
                    }
                    mBinding.bottomContainer02.addView(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRetrievalFailed(String error) {
                Log.i(TAG, "onRetrievalFailed: ");
            }
        });


//        AdView mAdView = AdMobCase.createBanner(getContext(), "ca-app-pub-5111357348858303/2564038379");
//        mAdView.setAdListener(new AdListener(mAdView));
//        mAdView.loadAd(AdMobCase.buildAdRequest());
//        mBinding.bottomContainer02.addView(mAdView);
    }


    //=================================
    //From presenter
    //================================

    @Override
    public void showError(String err) {
//        APIError err = new APIError();
//        err.setErrorMsg(errorMsg);
//        adapter.swap(err);
//        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void showError(int err) {
//        showError(getString(err));
//    }

    @Override
    public void showData(ArrayList<Obj> data) {
        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(mBinding.mainContainer.getId(), RecyclerFragment.newInstance(data), "@tag")
                .commit();
    }

}
