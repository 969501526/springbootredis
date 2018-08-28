package com.clj.springboot;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static void main(String args[]){
        String[] title = {"姓名","性别","年龄"};
        //创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建表
        HSSFSheet sheet = workbook.createSheet();
        //
        HSSFRow row =sheet.createRow(0);
        HSSFCell cell = null;
        //插入title
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //追加数据
        for (int i=1;i<=10;i++){
            HSSFRow row2 = sheet.createRow(i);
            HSSFCell cell2 = row2.createCell(0);
            cell2.setCellValue("a"+i);
            cell2 = row2.createCell(1);
            cell2.setCellValue("男");
            cell2 = row2.createCell(2);
            cell2.setCellValue("18");
        }
        File file = new File("C:/Users/69472/Desktop/ll.xls");
        try {
            file.createNewFile();
            FileOutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
        } catch (IOException e) {
            System.out.println(-1);
            e.printStackTrace();
        }
        System.out.println(1);
    }
}
