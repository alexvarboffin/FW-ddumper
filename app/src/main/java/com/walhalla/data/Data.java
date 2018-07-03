package com.walhalla.data;

import android.util.Log;

import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.LsObj;
import com.walhalla.domen.item.Obj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {

    private static final String TAG = "@@@";

    public static String[] proc = new String[]{

//            "asound",
//            "buddyinfo",
//            "bus",
//            "cgroups",
//            "cmdline",
//            "consoles",
//            "cpu",
            "cpuinfo",
//            "cpw",
//            "cpwcn",
//            "crypto",
//            "devices",
//            "diskstats",
//            "driver",
//            "execdomains",
//            "fb",
//            "filesystems",
//            "fs",
//            "interrupts",
//            "iomem",
//            "ioports",
//            "irq",
//            "kallsyms",
//            "kmsg",
//            "kpagecount",
//            "kpageflags",
//            "kpageswapn",
//            "loadavg",
//            "locks",
//            "meminfo",
//            "misc",
//            "modules",
//            "mounts",
//            "net",
//            "pagetypeinfo",
//            "partitions",
//            "pin_switch",
//            "scsi",
//            "self",
//            "softirqs",
//            "stat",
//            "swaps",
//            "sys",
//            "sysrq-trigger",
//            "sysvipc",
//            "timer_list",
//            "tty",
//            "uid_stat",
//            "uptime",
//            "version",
//            "vmallocinfo",
//            "vmstat",
//            "wakelocks",
//            "zoneinfo",
    };


    public static List<String> jj() {
        List<String> list = new ArrayList<>();

        for (String aProc : proc) {
            list.add("echo #####" + aProc + "#####");
            list.add("cat /proc/" + aProc);
        }

        list.add("id");
        list.add("busybox --version");
        list.add("nc --version");
//        list.add("ls -l " + FileUtil.EXTERNAL_STORAGE_DIRECTORY);
//        list.add("ls -l " + FileUtil.EXTERNAL_STORAGE_DIRECTORY_ROOT);
        //"ls /proc",

        return list;
    }

    public List<ShellCommand> getCommands() {
        return shell;
    }

    private List<ShellCommand> shell = new ArrayList<>();

    public Data() {


        /**
         brw------- root     root     179, 129 2018-06-20 02:26 mmcblk1p1
         drwxr-xr-x root     root              2018-06-20 02:26 platform
         */
        shell.add(new LaCommand("/dev/block"));
        shell.add(new ShellCommand("ls -l /dev/block/platform/sdio_emmc/by-name") {

            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                Pattern pattern = Pattern.compile("(\\w+) -> ([\\S].*)", Pattern.DOTALL);

                ArrayList<Obj> objects = new ArrayList<>();

                for (String content : result) {
                    Matcher matcher = pattern.matcher(content);

                    while (matcher.find()) {
//                                String uu =
//                                    "Found at: " + matcher.start()
//                                    + " - " + matcher.end() +
//                                        "@@" + matcher.group(1) + "@@" + matcher.group(2);


                        String name;
                        String location;

                        name = matcher.group(1);
                        location = matcher.group(2);

                        objects.add(new LsObj(location, name, "", new File(name).isDirectory()));
                    }
                }
                return objects;
            }
        });
        shell.add(new ShellCommand("cat /proc/partitions") {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {

                Pattern pattern = Pattern.compile("(^\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(.*)", Pattern.DOTALL);

                ArrayList<Obj> objects = new ArrayList<>();

                String location, name;
                String info;

                for (String content : result) {
                    Matcher matcher = pattern.matcher(content.trim());

                    while (matcher.find()) {

                        info = matcher.group(1) + "\t" + matcher.group(2) + "\t" + matcher.group(3);

                        name = matcher.group(4);
                        location = "/dev/block/" + name;
//                                String uu =
//                                    "Found at: " + matcher.start()
//                                    + " - " + matcher.end() +
//                                        "@@" + matcher.group(1) + "@@" + matcher.group(2);


                        objects.add(new LsObj(location/*matcher.group(1)*/, name, info,
                                //new File(location).isDirectory())
                                true
                        ));
                    }
                }
                return objects;
            }
        });//@@@@


        shell.add(new BaseCommand("ip route show"));
    }


//    private void checker(String name) {
//        System.out.println("###########" + name + "### is dir ->" + new File(name).isDirectory());
//        System.out.println("###########" + name + "### is file ->" + new File(name).isFile());
//    }


    //([\d]{1,20})\s{1,3}([\S].*)


    public static class LaCommand extends ShellCommand {

        public LaCommand(String command) {
            super(("ls -la " + command));
        }

        @Override
        public ArrayList<Obj> parseResult(List<String> result) {

            Pattern pattern = Pattern
                    .compile("^(\\w*----|\\w*-\\w)(.*)\\s{1}(\\S*)\\s{1}(\\S*)$", Pattern.DOTALL);

            ArrayList<Obj> objects = new ArrayList<>();

            String name;
            String location;
            String info;


            Log.i(TAG, "parseResult: " + result.size());

            for (String content : result) {
                Matcher matcher = pattern.matcher(content.trim());

                while (matcher.find()) {
                    name = matcher.group(4);
                    location = "/dev/block/" + name;
                    info = matcher.group(1)
                            + " " + matcher.group(2)
                            + " " + matcher.group(3);

//                        boolean nnnn = new File(location).isDirectory();
//                        Log.i(TAG, location + ":" + nnnn+":"+new File(location).isFile());

                    objects.add(new LsObj(location, name, info, true));
                }
            }
            return objects;
        }
    }

    public static class BaseCommand extends ShellCommand {

        public BaseCommand(String command) {
            super(command);
        }

        @Override
        public ArrayList<Obj> parseResult(List<String> result) {
            ArrayList<Obj> objects = new ArrayList<>();
            for (String s : result) {
                objects.add(new Obj(s));
            }
            return objects;
        }
    }
}
