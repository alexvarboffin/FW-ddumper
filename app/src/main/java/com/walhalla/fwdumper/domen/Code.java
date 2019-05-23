package com.walhalla.fwdumper.domen;

public class Code {

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private final String code;
    private final String description;

    public Code(String code, String description) {
        this.code = code;
        this.description = description;
    }
}