package com.walhalla.ui.fragment.tab;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;

import com.walhalla.fwdumper.task.TaskCallback;

import java.util.List;

/**
 * Created by combo on 12/19/2017.
 */

public class OutputFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private EditText editText;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.exit:
//                System.exit(1);
//                break;

            case R.id.action_clear:
                clear();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cmd_menu, menu);
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
        return inflater.inflate(R.layout.fragment_console, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textView);
        editText = view.findViewById(R.id.et_user_input);
        Button button = view.findViewById(R.id.btn_user_input);
        button.setOnClickListener(this);
    }

    public void println(Object progress) {
        textView.append("\n" + progress.toString());
    }

    public void clear() {
        textView.setText("");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_user_input) {

            String command = editText.getText().toString().trim();
            if (!command.isEmpty()) {
                new MainActivity.ExecuteTask(new TaskCallback() {
                    @Override
                    public void onTaskCompleted(List<String> result) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("---------------------")
                                .append((char) 10);
                        if (result != null) {
                            for (String content : result) {
                                sb.append(content).append((char) 10);
                            }
                        }
                        textView.append(sb.toString());
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
    }
}
