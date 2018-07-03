package com.walhalla.fwdumper.task;

import java.util.List;

public interface TaskCallback {
    void onTaskCompleted(List<String> result);

    void onProgressUpdate(String progress);

    void onCancelled();

    void onPreExecute();
}
