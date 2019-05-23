package com.walhalla.domen;


import com.walhalla.domen.item.LsObj;
import com.walhalla.domen.item.Obj;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class Main {
//    public static void main(String[] args) {
//        List<String> result = new ArrayList<>();
//        result.add("dev: size erasesize name");
//        result.add("mtd0: 00040000 00020000 \"misc\"");
//        result.add("mtd1: 00500000 00020000 \"recovery\"");
//        result.add("mtd2: 00280000 00020000 \"boot\"");
//        result.add("mtd3: 04380000 00020000 \"system\"");
//        result.add("mtd4: 04380000 00020000 \"cache\"");
//        result.add("mtd5: 04ac0000 00020000 \"userdata\"");
//
//
//        //(^\d+)\s+(\d+)\s+(\d+)\s+(.*)
//
//        Pattern pattern = Pattern.compile("(^\\w+):\\s(\\w+)\\s(\\w+)\\s\"(\\w+)\"", Pattern.DOTALL);
//
//        ArrayList<Obj> objects = new ArrayList<>();
//
//        String location, name;
//        String info;
//
//        for (String content : result) {
//            Matcher matcher = pattern.matcher(content.trim());
//
//
//            while (matcher.find()) {
//
////                System.out.println("1|" + matcher.group(1));
////                System.out.println("2|" + matcher.group(2));
////                System.out.println("3|" + matcher.group(3));
////                System.out.println("4|" + matcher.group(4));
//
//                info = matcher.group(1) + "\t" + matcher.group(2) + "\t" + matcher.group(3);
//
//                name = matcher.group(4);
//                location = "/dev/mtd/" + matcher.group(1);
////                                String uu =
////                                    "Found at: " + matcher.start()
////                                    + " - " + matcher.end() +
////                                        "@@" + matcher.group(1) + "@@" + matcher.group(2);
//
//
//                objects.add(new LsObj(location/*matcher.group(1)*/, name, info,
//                        //new File(location).isDirectory())
//                        true
//                ));
//            }
//        }
//
//        System.out.println(objects);
//    }
//}