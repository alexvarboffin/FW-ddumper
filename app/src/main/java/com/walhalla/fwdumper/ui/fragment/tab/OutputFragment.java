package com.walhalla.fwdumper.ui.fragment.tab;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;
import com.walhalla.fwdumper.databinding.FragmentConsoleBinding;
import com.walhalla.fwdumper.task.TaskCallback;
import com.walhalla.fwdumper.ui.fragment.dialog.CodeDialogFragment;
import com.walhalla.fwdumper.ui.fragment.dialog.TestersDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by combo on 12/19/2017.
 */

public class OutputFragment extends Fragment
        implements View.OnClickListener, TextView.OnEditorActionListener {

    private FragmentConsoleBinding mBinding;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.exit:
//                System.exit(1);
//                break;

            case R.id.action_clear:
                clear();
                break;

            case R.id.action_share_terminal_data:
                if (getContext() != null) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareText = "-------------------------\n";
                    shareText = shareText + mBinding.output.getText().toString();
                    // if you have live app then you can share link like below
                    shareText = shareText + "https://play.google.com/store/apps/details?id="
                            + getContext().getPackageName();
                    intent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(intent, "Share via"));
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.output, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_console, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.output.setMovementMethod(new ScrollingMovementMethod());
        mBinding.etUserInput.setOnEditorActionListener(this);
        mBinding.fab.setOnClickListener(this);
        //mBinding.overflowMenu.setOnClickListener(this);
    }

    public void println(Object progress) {
        mBinding.output.append((char) 10 + progress.toString());
    }

    public void clear() {
        mBinding.output.setText("");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {

            String command = mBinding.etUserInput.getText().toString().trim();
            if (!command.isEmpty()) {
                new MainActivity.ExecuteTask(new TaskCallback() {
                    @Override
                    public void onTaskCompleted(List<String> result) {
                        StringBuilder sb = new StringBuilder();

                        if (result != null) {
                            for (String content : result) {
                                sb.append(content).append((char) 10);
                            }
                        }
                        println(sb.toString());
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
                }).execute(new String[]{command});
            }
        }
//        else if (v.getId() == R.id.overflow_menu) {
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            Fragment prev = getFragmentManager().findFragmentByTag("code_dialog");
//            if (prev != null) {
//                ft.remove(prev);
//            }
//            ft.addToBackStack(null);
//
//            // Create and show the dialog.
//            DialogFragment newFragment = new CodeDialogFragment();
//            newFragment.show(ft, "code_dialog");
//        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                || (actionId == EditorInfo.IME_ACTION_DONE)) {
            //do what you want on the press of 'done'
            mBinding.fab.performClick();
        }
        return false;
    }
}
