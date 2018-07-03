package com.walhalla.presentation.view;

import com.walhalla.domen.item.Obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by combo on 12/29/2017.
 */

public interface BlockListView {
    void showError(String err);

    //void showError(int err);
    void showData(ArrayList<Obj> data);
}
