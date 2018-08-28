package com.clj.springboot.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class ExcelUtil {

    public static HSSFWorkbook getHSSFWorkbook(String sheetName,List<String> title,String [][]values, HSSFWorkbook wb, List<Integer> cellRangeAddress, Integer mergeRow){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);//选择需要用到的字体格式


        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列

        if(cellRangeAddress!=null&&mergeRow!=null) {
            Integer left = 1;
            Integer right = 0;
            for (Integer integer : cellRangeAddress) {
                right = right + integer;
                //
                for (int i = 0; i <= mergeRow; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(left,right,i,i));
                    /**/
                }
                left = left + integer;
            }
        }
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.size();i++){
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        for (int i = 0; i < 30; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return wb;
    }

    public static String getUploadFilePath() {
        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String serverRealPath = request.getServletContext().getRealPath(File.separator );
        String uploadFilePath =serverRealPath + "excelFile" + File.separator + "Upload" + File.separator ;
        return uploadFilePath;
    }

    public static void writeExcel(String destFileNameStr,List<Integer> cellRangeAddress, Integer mergeRow) throws IOException {

        FileInputStream excelFileInputStream = new FileInputStream(destFileNameStr);
        HSSFWorkbook workbook = new HSSFWorkbook(excelFileInputStream);
        excelFileInputStream.close();
        HSSFSheet sheet = workbook.getSheetAt(0);

        if(cellRangeAddress!=null&&mergeRow!=null) {
            Integer left = 1;
            Integer right = 0;
            for (Integer integer : cellRangeAddress) {
                right = right + integer;
                for (int i = 0; i <= mergeRow; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(left,right,i,i));
                }
                left = left + integer;
            }
        }

        FileOutputStream excelFileOutPutStream = new FileOutputStream(destFileNameStr);
        workbook.write(excelFileOutPutStream);
        excelFileOutPutStream.flush();
        excelFileOutPutStream.close();
    }


    /**
     * 导出Excel
     *
     * @createDate 2015-12-11
     * @param templateFileName
     *            模板文件（含路径）
     * @param beans
     *            数据
     * @param destFileName
     *            目标文件（含路径）
     * @return
     * @throws InvalidFormatException
     */
    public static void excelExport(String templateFileName, Map<String, Object> beans, String destFileName)
            throws InvalidFormatException {
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(templateFileName, beans, destFileName);
        }catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载Excel
     *
     * @createDate 2015-12-11
     * @param sourceFileName
     *            源文件（含路径）
     * @param response
     */
    public static void excelDownLoad(String sourceFileName, HttpServletResponse response) {
        try {
            String fileName = sourceFileName.substring(sourceFileName.lastIndexOf(File.separator ) + 1, sourceFileName.length());
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) );
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            OutputStream fos = null;
            InputStream fis = null;
            File uploadFile = new File(sourceFileName);
            fis = new FileInputStream(uploadFile);
            bis = new BufferedInputStream(fis);
            fos = response.getOutputStream();
            bos = new BufferedOutputStream(fos);

            // 弹出下载对话框
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
            fis.close();
            bis.close();
            fos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
