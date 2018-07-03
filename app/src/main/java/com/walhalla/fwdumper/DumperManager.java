package com.walhalla.fwdumper;

import com.walhalla.data.Data;
import com.walhalla.domen.ShellCommand;

import java.util.List;

public class DumperManager {

    public List<ShellCommand> getCommands() {
        return mCommands;
    }

    private List<ShellCommand> mCommands;
    private static DumperManager INSTANCE = new DumperManager();

    private DumperManager() {
        mCommands = new Data().getCommands();
    }

    public static DumperManager getInstance() {
        return INSTANCE;
    }


}
