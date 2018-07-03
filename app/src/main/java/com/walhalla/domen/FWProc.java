package com.walhalla.domen;

import com.walhalla.domen.item.Obj;

public class FWProc extends Obj{

    public String getCommand() {
        return command;
    }

    private final String command;

    public FWProc(String s) {
        super(s);
        this.command = s;
    }
}
