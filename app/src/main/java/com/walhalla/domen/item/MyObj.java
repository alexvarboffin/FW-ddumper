package com.walhalla.domen.item;

public class MyObj extends Obj {


    private final String location;

    public boolean isDirectory() {
        return directory;
    }

    private final boolean directory;

    public MyObj(String location, String name, boolean directory) {
        super(name);
        this.directory = directory;
        this.location = location;
    }


    public String getLocation() {
        return location;
    }
}
