package com.walhalla.fwdumper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.walhalla.LocalStorage;
import com.walhalla.common.FileUtil;
import com.walhalla.common.Util;
import com.walhalla.data.Data;
import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.MyObj;
import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.task.TaskCallback;
import com.walhalla.fwdumper.ui.fragment.dialog.TestersDialogFragment;
import com.walhalla.presentation.view.MainView;
import com.walhalla.fwdumper.ui.adapter.ComplexAdapter;
import com.walhalla.fwdumper.ui.fragment.MoreFragment;
import com.walhalla.fwdumper.ui.fragment.TabHolderFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import eu.chainfire.libsuperuser.Shell;
import ru.walhalla.ui.Module_U;
import ru.walhalla.ui.observer.RateAppModule;

import static com.walhalla.common.Util.BB_PACKAGES;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ComplexAdapter.ChildItemClickListener, MainView {

    private static final String TAG_TAB_HOLDER_FRAGMENT = "@tag";
    private static final String TAG = "@@@";

    private static final String PREF_NOTIFICATION_1 = "pref-01";
    private static final String PREF_NOTIFICATION_2 = "pref-02";

    private boolean b_1;
    private boolean b_2;

    private SharedPreferences sharedPreferences;
    private LocalStorage mLocalStorage;

    private DrawerLayout mDrawerLayout;
    private RateAppModule mRateAppModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(v -> {
            Module_U.aboutDialog(this);
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Module_U.checkUpdate(this);
        mLocalStorage = LocalStorage.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        fab.setVisibility(View.GONE);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

//        // mode switch button
//        Button button = (Button)findViewById(R.id.switch_button);
//        button.setText(R.string.enable_interactive_mode);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(v.getContext(), InteractiveActivity.class));
//                finish();
//            }
//        });
//
//        // refresh button
//        ((Button)findViewById(R.id.refresh_button)).
//                setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        (new Startup()).setContext(v.getContext()).execute();
//                    }
//                });

        // Let's do some background stuff
        new Startup(new Startup.TaskCallback() {
            @Override
            public void onTaskCompleted(String result) {

                consoleLog(result);

                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onPreExecute() {
                // We're creating a progress dialog here because we want the user to wait.
                // If in your app your user can just continue on with clicking other things,
                // don't do the dialog thing.

                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle(R.string.app_name);
                dialog.setMessage("Doing something interesting ...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                dialog.show();
            }

            @Override
            public void onCancelled() {
                dialog.dismiss();
            }
        }).execute();

        sharedPreferences = getPreferences(MODE_PRIVATE);

        b_1 = sharedPreferences.getBoolean(PREF_NOTIFICATION_1, false);
        b_2 = sharedPreferences.getBoolean(PREF_NOTIFICATION_2, false);

        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TabHolderFragment tabHolderFragment = TabHolderFragment.newInstance("", "");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, tabHolderFragment, TAG_TAB_HOLDER_FRAGMENT).commit();
        }

        mRateAppModule = new RateAppModule(this);

        //WhiteScreen
        getLifecycle().addObserver(mRateAppModule);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mRateAppModule.appReloadedHandler();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            TabHolderFragment fragment = (TabHolderFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_TAB_HOLDER_FRAGMENT);
            if (fragment != null) {
                if (fragment.onBackPressed()) {

                    //backstack_logic

                    return;
                }
            }

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_02_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_about:
                Module_U.aboutDialog(this);
                return true;

            case R.id.action_privacy_policy:
                Module_U.openBrowser(this, Config.url_privacy_policy);
                return true;

            case R.id.action_rate_app:
                Module_U.rateUs(this);
                return true;
            case R.id.action_share_app:
                Module_U.shareThisApp(this);
                return true;
            case R.id.action_discover_more_app:
                Module_U.moreApp(this);
                return true;


            case R.id.action_testers:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = new TestersDialogFragment();
                newFragment.show(ft, "dialog");
                return true;

            case R.id.action_exit:
                this.finish();
                System.exit(0);
                return true;

            case R.id.action_feedback:
                Module_U.feedback(this);
                return true;


//                extended
            case R.id.action_clear:
                return false;

            case R.id.action_share_terminal_data:
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }

        //action_how_to_use_app
        //action_support_developer
    }

    @Override
    public void executeTheCommand(ShellCommand shellCommand, boolean b) {
        clearConsole();
        //Toast.makeText(this, "@" + shellCommand.getCommand(), Toast.LENGTH_SHORT).show();

        new ExecuteTask(new MainActivity.MyCallback() {

            @Override
            public void onTaskCompleted(List<String> result) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                StringBuilder sb = new StringBuilder();
                if (result != null) {
                    for (String content : result) {
                        sb.append(content).append((char) 10);
                    }
                }

                ArrayList<Obj> list = shellCommand.parseResult(result);
                consoleLog(sb.toString());


                if (list == null) {
                    return;
                }

                if (list.isEmpty()) {
                    list.add(new Obj(sb.toString()));
                }

                printOutput(list);
            }

            @Override
            public void onProgressUpdate(String progress) {

            }

            @Override
            public void onCanceled() {
                consoleLog(getString(R.string.canceled));
            }

            @Override
            public void onPreExecute() {

            }
        }).execute(new String[]{shellCommand.getCommand()});
    }

    private void clearConsole() {
        //List<Fragment> fragments = getSupportFragmentManager().getFragments();
        TabHolderFragment fragment = (TabHolderFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_TAB_HOLDER_FRAGMENT);
        if (fragment != null) {
            fragment.clear();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Extract selected partition
     *
     * @param location
     * @param fileNameRaw
     */
    @Override
    public void createDumpSdCardRequest(String location, String fileNameRaw) {

//        if (!Util.ibBbInstalled(this)) {
//            Util.installPopUp(this);
//            return;
//        }


        String fileName = FileUtil.fixName(new MyObj(location, fileNameRaw, false));

        if (!b_1) {

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.title_attention)
                    .setMessage(String.format(getString(R.string.create_dump_on_sd), location, fileName))

                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {
                        b_1 = true;
                        sharedPreferences.edit()
                                .putBoolean(PREF_NOTIFICATION_1, true)
                                .apply();

                        createDumpSdCard(location, fileName);

                    }).setNegativeButton(android.R.string.cancel, null)
                    .create();
            dialog.show();
            return;
        }

        createDumpSdCard(location, fileName);
    }


    private void createDumpSdCard(String blockLocation, String fileName) {

        boolean makeDir = FileUtil.makeDir(FileUtil.EXT_STORAGE_IN);
        String out = FileUtil.extOutRoot(this);
        makeDir = FileUtil.makeDir(out);


        String[] commands = new String[]{
//                "mkdir /mnt/media_rw/sdcard0 0700 media_rw media_rw",
//                "mkdir /storage/sdcard0 0700 root root",
//                "export EXTERNAL_STORAGE /storage/sdcard0",

                "mkdir " + out,
                //"su -c \"rm " + outFile + "\"",
                "dd if=" + blockLocation + " of=" + out + File.separator + fileName,
                //"dd if=" + block + " conv=sync,noerror bs=64K | gzip -c | nc 192.168.1.1 3333",

                //"dd if=" + block + " | busybox nc -l -p 8888",

                //"ls " + LOC_STORAGE_OUT
        };


        new ExecuteTask(new MyCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onTaskCompleted(List<String> result) {
                StringBuilder sb = new StringBuilder();
                sb.append("---------------------")
                        .append((char) 10);
                if (result != null) {
                    for (String content : result) {
                        if (!content.contains("File exist")) {
                            sb.append(content);
                        }
                        sb.append((char) 10);
                    }
                }
                consoleLog(sb.toString());

                if (dialog != null) {
                    dialog.dismiss();
                }


                boolean mResult = true;
                Util.resultPopUp(MainActivity.this, sb.toString(), mResult);
            }

            @Override
            public void onProgressUpdate(String progress) {

            }
        }).execute(commands);
    }


    /**
     * Extrat dump nc
     *
     * @param message
     * @param errNc
     */
    private void showOutput(Context context, String message, boolean errNc) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false);

        if (errNc) {

            builder.setTitle(R.string.title_attention);
            builder.setMessage(context.getString(R.string.message_bb_req) + (char) 10 + message);

            builder.setPositiveButton(R.string.play_dialog_yes_msg, (dialog1, which) -> {

                try {
                    String s = String.format(getString(R.string.market_url), BB_PACKAGES[0]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s))
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } catch (Exception ignored) {
                    Toast.makeText(this, ignored.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }).setNegativeButton(android.R.string.cancel, null);

        } else {
            builder.setTitle(R.string.app_name);
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, null);
        }

        builder.create();
        builder.show();
    }


    @Override
    public void createDumpAdbRequest(String location, String output_file_name) {


//        if (!Util.ibBbInstalled(this)) {
//            Util.installPopUp(this);
//            return;
//        }


        String host = mLocalStorage.remoteHost();
        String port = mLocalStorage.remotePort();

        if (!b_2) {

            String msg = String.format(getString(R.string.send_over_network), host, port);


            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.title_attention)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {
                        b_2 = true;
                        sharedPreferences.edit()
                                .putBoolean(PREF_NOTIFICATION_2, true)
                                .apply();

                        createDumpSdCard(location, output_file_name);

                    })
                    .setNegativeButton(android.R.string.cancel, (dialog12, which) -> {
//
                    })
                    .create();
            dialog.show();
            return;
        }

        createDumpAdb(location, output_file_name, host, port);
    }

    private void createDumpAdb(String location, String output_file_name, String host, String port) {


        String[] commands = new String[]{

                //"dd if=" + location + "| busybox nc " + host + " " + port

                /**
                 * Кидает файл на локальный порт,
                 * портфорвардинг ADB перехватывает на компьютере...
                 * busybox nс
                */


                //-l -p localport
                //Work
                //"dd if=" + location + " | nc -l -w 10 -p " + port + " &> /dev/null && echo \"Online\" || echo \"Offline\""


                String.format("dd if=%1$s | %2$s -vv -w %3$s %4$s %5$s",
                        location, Util.getNetcatLocation(), "20", host, port)
        };

        consoleLog(String.format("dd if=%1$s | %2$s -w %3$s %4$s %5$s",
                location, Util.getNetcatLocation(), "20", host, port));

        new ExecuteTask(new MyCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onTaskCompleted(List<String> result) {


                boolean err_nc = false;


                StringBuilder sb = new StringBuilder();

                if (result != null) {
                    for (String content : result) {
                        if (content.contains("nc: not found")) {
                            err_nc = true;
                        }
                        sb.append(content).append((char) 10);
                    }
                }


                consoleLog(sb.toString());
                showOutput(MainActivity.this, sb.toString(), err_nc);

                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onProgressUpdate(String progress) {

            }
        })
                .execute(commands);
    }


    @Override
    public void requestExtendedInfo(String command) {

        MoreFragment fr = MoreFragment.newInstance(command);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fr)//"WW"
                .addToBackStack(null)
                .commit();
    }

    /**
     * output to console
     *
     * @param output
     */
    @Override
    public void consoleLog(String output) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        TabHolderFragment f = (TabHolderFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_TAB_HOLDER_FRAGMENT);
        if (f != null) {
            f.println(output);
        }
        Log.i(TAG, "consoleLog: " + output);
    }

    @Override
    public void printOutput(ArrayList<Obj> list) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        TabHolderFragment fragment = (TabHolderFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_TAB_HOLDER_FRAGMENT);
        if (fragment != null) {
            fragment.priWntList(list);//# build gui
        }
    }


    private ProgressDialog dialog = null;

    private abstract class MyCallback implements TaskCallback {

        @Override
        public void onPreExecute() {
            // We're creating a progress dialog here because we want the user to wait.
            // If in your app your user can just continue on with clicking other things,
            // don't do the dialog thing.

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage("Doing something interesting ...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), (dialog, which) -> {
                //Dismiss
            });
            dialog.show();
        }

        @Override
        public void onCanceled() {
            dialog.dismiss();
        }
    }


    public static class ExecuteTask extends AsyncTask<String[], String, List<String>> {

        private boolean suAvailable = false;
        private final TaskCallback mTaskTaskCallback;


        public ExecuteTask(final TaskCallback listener) {
            this.mTaskTaskCallback = listener;
        }

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            if (mTaskTaskCallback != null) {
                mTaskTaskCallback.onPreExecute();
            }
        }

        @Override
        protected List<String> doInBackground(String[]... params) {

//        String pstdout;
//        StringBuilder wholeoutput = new StringBuilder("");
//        String[] commands = params[0];
//
//        DataOutputStream outputStream;
//        BufferedReader inputStream;
//
//
//        Process scanProcess;
//
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(SHELL_TO_RUN);
//            publishProgress(SHELL_TO_RUN);
//
//            processBuilder.redirectErrorStream(true);
//            scanProcess = processBuilder.start();
//
//            outputStream = new DataOutputStream(scanProcess.getOutputStream());
//            inputStream = new BufferedReader(new InputStreamReader(scanProcess.getInputStream()));
//
//            for (String single : commands) {
//                publishProgress("[~] Single Executing: " + single);
//                outputStream.writeBytes(single + "\n");
//                outputStream.flush();
//            }
//            outputStream.writeBytes("exit\n");
//            outputStream.flush();
//            while (((pstdout = inputStream.readLine()) != null)) {
//                if (isCancelled()) {
//                    scanProcess.destroy();
//                    break;
//                } else {
//                    pstdout = pstdout + "\n";
//                    wholeoutput.append(pstdout);
//                    publishProgress(pstdout, null);
//                    pstdout = null;
//                }
//            }
//
//            if (!isCancelled()) scanProcess.waitFor();
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        return wholeoutput.toString();

            List<String> suResult;
            StringBuilder sb = new StringBuilder();
            suAvailable = Shell.SU.available();

            if (suAvailable) {
                suResult = Shell.SU.run(params[0]);
            } else {
                suResult = new ArrayList<>();
                suResult.add("This device does not have proper root access");
                suResult.addAll(Shell.SH.run(params[0]));//No root
            }


            return suResult;
        }

        @Override
        protected void onPostExecute(List<String> suResult) {
//        if (mTaskTaskCallback != null) {
//            StringBuilder sb = (new StringBuilder());
//
//            if (suResult != null) {
//                for (String line : suResult) {
//                    sb.append(line).append((char) 10);
//                }
//            }

            mTaskTaskCallback.onTaskCompleted(suResult);
//        }
        }

        @Override
        protected void onCancelled() {
            if (mTaskTaskCallback != null) {
                mTaskTaskCallback.onCanceled();
            }
        }

        @Override
        protected void onProgressUpdate(String[] progress) {
            super.onProgressUpdate(progress);
            if (progress != null && mTaskTaskCallback != null) {
                mTaskTaskCallback.onProgressUpdate(progress[0]);
            }
        }
    }


    private static class Startup extends AsyncTask<Void, Void, String> {

        public interface TaskCallback {
            void onTaskCompleted(String result);

            //            void onProgressUpdate(String progress);
//
            void onCancelled();

            void onPreExecute();
        }

        private final TaskCallback mTaskTaskCallback;


        Startup(final TaskCallback listener) {
            this.mTaskTaskCallback = listener;
        }

        private boolean suAvailable = false;
        private String suVersion = null;
        private String suVersionInternal = null;


        @Override
        protected void onPreExecute() {
            if (mTaskTaskCallback != null) {
                mTaskTaskCallback.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            List<String> suResult = null;

            suAvailable = Shell.SU.available();
            if (suAvailable) {
                suVersion = Shell.SU.version(false);
                suVersionInternal = Shell.SU.version(true);
                suResult = Shell.SU.run(Data.jj());
            }

            // output
            StringBuilder sb = (new StringBuilder()).
                    append("Root? ").append(suAvailable
                    ? "Yes" : "No").append((char) 10).
                    append("Version: ").append(suVersion == null ? "N/A" : suVersion).append((char) 10).
                    append("Version (internal): ").append(suVersionInternal == null ? "N/A" :
                    suVersionInternal).append((char) 10).
                    append((char) 10);

            if (suResult != null) {
                for (String line : suResult) {
                    sb.append(line).append((char) 10);
                }
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            if (mTaskTaskCallback != null) {
                mTaskTaskCallback.onTaskCompleted(result);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (mTaskTaskCallback != null) {
                mTaskTaskCallback.onCancelled();
            }
        }

    }

}
