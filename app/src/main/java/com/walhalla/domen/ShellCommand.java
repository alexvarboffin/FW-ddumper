package com.walhalla.domen;

import android.support.annotation.Nullable;

import com.walhalla.domen.item.Obj;

import java.util.ArrayList;
import java.util.List;

public abstract class ShellCommand {

    private final String command;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public ShellCommand(String command) {
        this.command = command;
    }

    public ShellCommand(@Nullable String command, String name, int icon, int color) {
        this.name = name;
        this.command = command;
        this.mIcon = icon;
        this.mColor = color;
    }

    public String getCommand() {
        return command;
    }

    public abstract ArrayList<Obj> parseResult(List<String> result);


    private int mIcon;
    private int mColor;

    public int getIcon() {
        return mIcon;
    }

    public int getColor() {
        return mColor;
    }
}
