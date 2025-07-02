package com.ins1st.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);


    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            log.info("文件夹" + path + "创建成功");
        }
    }

    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                log.info("文件" + path + "创建成功");
            } catch (IOException e) {
                log.info("文件" + path + "创建失败");
            }
        } else {
            try {
                file.delete();
                log.info("文件已存在，已删除文件" + path);
                file.createNewFile();
                log.info("文件" + path + "创建成功");
            } catch (IOException e) {
                log.info("文件" + path + "创建失败");
            }
        }
    }

    public static void deleteTTT(String path) {
        File root = new File(path);
        if(root.isDirectory()){
            for(File f : root.listFiles()){
                deleteTTT(f.getAbsolutePath());
            }
        }else{
            if(root.getName().contains("TTT")){
                log.debug("删除文件：{}",root.getAbsolutePath());
                root.delete();
            }
        }
    }

}
