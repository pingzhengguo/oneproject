package com.pzg.code.commons.excel;

import com.pzg.code.commons.utils.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {

    }

    /**
     * 文件名加UUID
     *
     * @param filename 文件名
     * @return UUID_文件名
     */
    private static String makeFileName(String filename) {
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 文件名特殊字符过滤
     *
     * @param fileName 文件名
     * @return 过滤后的文件名
     * @throws PatternSyntaxException 正则异常
     */
    public static String stringFilter(String fileName) {
        String regEx = "[`~!@#$%^&*+=|{}':; ',//[//]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(fileName);
        return m.replaceAll("").trim();
    }

    /**
     * 生成Excel文件
     *
     * @param filename 文件名称
     * @param list     文件内容List
     * @param clazz    List中的对象类型
     */
    public static ResultInfo createExcelByPOIKit(String filename, List<?> list, Class<?> clazz) {

        if (null == list || list.isEmpty()) {
            return ResultInfo.failure("导出数据为空！");
        } else {
            boolean operateSign = false;
            String fileName = filename + ".xlsx";
//            fileName = makeFileName(fileName);
            try {
                File fileDir = new File("file");
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                String path = "C:\\Users\\Administrator\\Desktop\\" + fileName;
                FileOutputStream fos;
                fos = new FileOutputStream(path);
                operateSign = ExcelUtils.builder(clazz)
                        // 设置每个sheet的最大记录数,默认为10000,可不设置
                        // .setMaxSheetRecords(10000)
                        .toExcel(list, "查询结果", fos);
            } catch (FileNotFoundException e) {
                log.error("文件未找到", e);
            }
            if (operateSign) {
                return ResultInfo.success().build(fileName);
            } else {
                return ResultInfo.failure("导出Excel失败，请联系网站管理员！");
            }
        }
    }

    /**
     * 生成Csv文件
     *
     * @param filename 文件名称
     * @param list     文件内容List
     * @param clazz    List中的对象类型
     */
    public static ResultInfo createCsv(String filename, List<?> list, Class<?> clazz) {

        if (null == list || list.isEmpty()) {
            return ResultInfo.failure("导出数据为空！");
        } else {
            boolean operateSign;
            String fileName = filename + ".csv";
//            fileName = makeFileName(fileName);

            File fileDir = new File("file");
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            String path = "C:\\Users\\Administrator\\Desktop\\" + fileName;
            operateSign = ExcelUtils.builder(clazz)
                    .toCsv(list, path);
            if (operateSign) {
                return ResultInfo.success().build(fileName);
            } else {
                return ResultInfo.failure("导出Csv失败，请联系网站管理员！");
            }
        }
    }

    /**
     * @author: zhangzhichuan
     * @description: 删除文件
     * @date: Create in 2019/3/14 13:33
     * @modified by:
     * @return:
     */
    public static void removeFile(File path) {
        log.info("删除已经导出的临时文件");
        if (path.isDirectory()) {
            File[] child = path.listFiles();
            if (child != null && child.length != 0) {
                for (int i = 0; i < child.length; i++) {
                    removeFile(child[i]);
                    child[i].delete();
                }
            }
        }
        path.delete();
    }

}
