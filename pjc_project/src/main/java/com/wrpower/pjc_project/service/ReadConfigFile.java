package com.wrpower.pjc_project.service;

import javafx.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

@RestController
public class ReadConfigFile {

    //  Map<pjc表名,Map<pjc属性名,Pair<dky表名,dky列名> > >
    private static Map<String, Map<String, Pair<String, String>>> attToDkyTableMap = new HashMap<>();
    //  Map<pjc表名,Map<pjc属性名,dky类型> >
    private static Map<String, Map<String, Integer>> attAndTypeMap = new HashMap<>();


    public ReadConfigFile() {
        read();
    }

    private static void read() {
        FileReader fr = null;
        try {
            fr = new FileReader("attrToDkyTable.txt");
//            fr = new FileReader("D:\\pjc_project\\src\\main\\resources\\attrToDkyTable.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fr == null)
            return;
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String pjcTableName = "";
        String[] arrs;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line.startsWith("<") && line.endsWith(">")) {
                pjcTableName = line.substring(1, line.length() - 1);
//                System.out.println(pjcTableName);
            } else if (line.startsWith("#")) {
                arrs = line.split(" ");
                if( arrs.length != 5 )
                    continue;
                String pjcColumnName = arrs[1];
                pjcColumnName = pjcColumnName.replaceAll("''", "");
                String dkyTable = arrs[2];
                dkyTable = dkyTable.replaceAll("''", "");
                String dkyColumnName = arrs[3];
                dkyColumnName = dkyColumnName.replaceAll("''", "");
                String type = arrs[4];
                type = type.replaceAll("''", "");
                if (pjcColumnName.isEmpty() || dkyTable.isEmpty() || dkyColumnName.isEmpty() || type.isEmpty())
                    continue;
                int dykType = Integer.valueOf(type);
                if (!pjcTableName.isEmpty()) {
                    if (attToDkyTableMap.containsKey(pjcTableName)) {
                        Pair<String, String> pair = new Pair<>(dkyTable, dkyColumnName);
                        attToDkyTableMap.get(pjcTableName).put(pjcColumnName, pair);
                    } else {
                        Map<String, Pair<String, String>> atrrToDkyMap = new HashMap<>();
                        Pair<String, String> pair = new Pair<>(dkyTable, dkyColumnName);
                        atrrToDkyMap.put(pjcColumnName, pair);
                        attToDkyTableMap.put(pjcTableName, atrrToDkyMap);
                    }
                    if (attAndTypeMap.containsKey(pjcTableName)) {
                        attAndTypeMap.get(pjcTableName).put(pjcColumnName, dykType);
                    } else {
                        Map<String, Integer> atrrToDkyMap = new HashMap<>();
                        atrrToDkyMap.put(pjcColumnName, dykType);
                        attAndTypeMap.put(pjcTableName, atrrToDkyMap);
                    }
//                    System.out.println(pjcColumnName + "====" + dkyTable + "====" + dkyColumnName + "====" + dykType);
                }
            }
        }
    }

    public static Map<String, Pair<String, String>> getAttToDkyTableMap(String tableName) {
        return attToDkyTableMap.get(tableName);
    }

    public static Map<String, Integer> getAttAndTypeMap(String tableName) {

        return attAndTypeMap.get(tableName);
    }


    @RequestMapping("/testConfigFile")
    public String testConfigFile(@RequestParam(value = "tableName") String tableName) {

        return getAttToDkyTableMap(tableName).toString();
    }


}
