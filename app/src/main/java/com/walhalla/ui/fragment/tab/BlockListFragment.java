package com.walhalla.ui.fragment.tab;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.DumperManager;
import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;
import com.walhalla.presentation.view.BlockListView;
import com.walhalla.ui.fragment.RecyclerFragment;

import java.util.ArrayList;

/**
 * Created by combo on 12/29/2017.
 */

public class BlockListFragment extends Fragment implements BlockListView {


    private DumperManager mDumperManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDumperManager = DumperManager.getInstance();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int i = 0;
        for (ShellCommand command : mDumperManager.getCommands()) {
            menu.add(Menu.NONE, i, Menu.NONE, command.getCommand());
            menu.getItem(i).setIcon(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher));
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
        ((MainActivity)getActivity()).executeTheCommand(fwCommand, false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blocks_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        getChildFragmentManager().beginTransaction()
//                .add(R.id.fl_container, null, "@tag");

        exec(0);
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
                .replace(R.id.fl_container, RecyclerFragment.newInstance(data), "@tag")
                .commit();
    }
}
