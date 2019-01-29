package com.wrpower.pjc_project.service;

import com.nari.cloud.dbaccess.impl.NRDBAccessImpl;
import com.nari.cloud.dbaccess.model.FileInfo;
import com.nari.cloud.dbaccess.tool.NRDataAccessException;
import com.nari.cloud.dbaccess.wrapper.NRDBAccess;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
public class FileAttachment {

    NRDBAccess m_nrdbAccess = new NRDBAccessImpl();

    {
        m_nrdbAccess.setProxyIp("10.33.3.31");
    }

    @RequestMapping("/getFileList")
    public void getFileList(@RequestParam("projectId") String projectId,
                            @RequestParam("devId") String devId,
                            @RequestParam("userName") String userName,
                            @RequestParam("userId") String userId) {

        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();

        List<String> dir_list = new ArrayList<>();
        dir_list.add("smartpower");
        dir_list.add(projectId);
        dir_list.add(devId);
        dir_list.add(userName);
        dir_list.add(userId);

        fileInfo.setDir_list(dir_list);
        fileInfoList.add(fileInfo);

        List<FileInfo> fileinfo_list_result = null;
        try {
            fileinfo_list_result = m_nrdbAccess.getFileList(fileInfoList);
            for (FileInfo ff : fileinfo_list_result) {
                System.out.println("func_result:" + ff.getFunc_result());
                for (int j = 0; j < ff.getFilename_list().size(); j++) {
                    System.out.println(ff.getFilename_list().get(j));
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getFileContent")
    public void getFileContent(@RequestParam("projectId") String projectId,
                               @RequestParam("devId") String devId,
                               @RequestParam("userName") String userName,
                               @RequestParam("userId") String userId,
                               @RequestParam("fileName") String fileName) {

        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();

        List<String> filename_list = new ArrayList<>();
        filename_list.add(fileName);

        List<String> dir_list = new ArrayList<>();
        dir_list.add("smartpower");
        dir_list.add(projectId);
        dir_list.add(devId);
        dir_list.add(userName);
        dir_list.add(userId);

        fileInfo.setFilename_list(filename_list);
        fileInfo.setDir_list(dir_list);
        fileInfoList.add(fileInfo);

        List<FileInfo> fileinfo_list_result = null;
        try {
            fileinfo_list_result = m_nrdbAccess.getFileContent(fileInfoList);
            for (FileInfo ff : fileinfo_list_result) {
                for (int j = 0; j < ff.getFilecontent_list().size(); j++) {
                    System.out.println(Arrays.toString(ff.getFilecontent_list().get(j)));
                }
                for (int j = 0; j < ff.getFilename_list().size(); j++) {
                    System.out.println(ff.getFilename_list().get(j));
                }
            }
        } catch (NRDataAccessException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/setFileContent")
    public Boolean setFileContent(@RequestParam("projectId") String projectId,
                                  @RequestParam("devId") String devId,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("userId") String userId,
                                  @RequestParam("fileName") MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        byte[] data = new byte[0];
        try {
            InputStream inputStream = file.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            data = out.toByteArray();
            String str = new String(data);
            System.out.println("===========" + str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<FileInfo> fileInfoList = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();
        List<String> filename_list = new ArrayList<>();
        List<byte[]> filecontent_list = new ArrayList<>();
        List<String> dir_list = new ArrayList<>();

        filename_list.add(fileName);
        filecontent_list.add(data);

        dir_list.add("smartpower");
        dir_list.add(projectId);
        dir_list.add(devId);
        dir_list.add(userName);
        dir_list.add(userId);

        fileInfo.setDir_list(dir_list);
        fileInfo.setFilename_list(filename_list);
        fileInfo.setFilecontent_list(filecontent_list);
        fileInfoList.add(fileInfo);

        List<FileInfo> fileinfo_list_result = null;
//        try {
//            fileinfo_list_result = m_nrdbAccess.setFileContent(fileInfoList);
//            for (FileInfo ff : fileinfo_list_result) {
//                if (ff.getFunc_result())
//                    return true;
//            }
//        } catch (NRDataAccessException e) {
//            e.printStackTrace();
//        }
        return false;
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("fileName") MultipartFile file) {
        if (file.isEmpty()) {
            return "fileName.isEmpty()";
        }
        byte[] data = new byte[0];
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            data = out.toByteArray();

            String str = new String(data);
            System.out.println("===========" + str);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = file.getOriginalFilename();
        System.out.println("===========" + fileName);

        return new String(data);
    }

}
