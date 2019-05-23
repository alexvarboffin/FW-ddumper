package com.walhalla.domen.item;

public class LsObj extends MyObj {

    public String getInfo() {
        return info;
    }

    private final String info;


    public LsObj(String location, String name, String info, boolean directory) {
        super(location, name, directory);
            this.info = info;
    }
}
