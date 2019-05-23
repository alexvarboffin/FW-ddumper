package com.walhalla.fwdumper.ui.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.walhalla.fwdumper.R;
import com.walhalla.fwdumper.domen.Code;
import com.walhalla.fwdumper.ui.adapter.CodeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CodeDialogFragment extends DialogFragment
        implements CodeAdapter.Callback {


    @Override
    public void onClick(Code code, int position) {
        //Toast.makeText(getContext(), code.getCode(), Toast.LENGTH_SHORT).show();
//        Intent in = new Intent(Intent.ACTION_MAIN);
//        in.setClassName("com.android.settings", "com.android.settings.TestingSettings");
//        startActivity(in);

        String ussdCode = "*" + Uri.encode("#") + "06" + Uri.encode("#");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Secret Codes");
        builder.setIcon(R.drawable.ic_testers);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_tester_dialog, null, false);
        builder.setView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        CodeAdapter adapter = new CodeAdapter(data(), getContext(), this);
        recyclerView.setAdapter(adapter);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }

    private List<Code> data() {
        List<Code> data = new ArrayList<>();
        data.add(new Code("*#*#4636#*#*",
                "Display information about Phone, Battery and Usage statistics"));
        data.add(new Code("*#*#7780#*#*",
                "Restting your phone to factory state-Only deletes application data and applications"));
        data.add(new Code("*2767*3855#",
                "It's a complete wiping of your mobile also it reinstalls the phones firmware"));
        data.add(new Code("*#*#34971539#*#*", "Shows completes information about the camera"));
        data.add(new Code("*#*#7594#*#*", "Changing the power button behavior-Enables direct poweroff once the code enabled"));
        return data;
    }


}
