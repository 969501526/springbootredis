package com.clj.springboot.controller;


import com.clj.springboot.model.TbUser;
import com.clj.springboot.model.UploadExcel;
import com.clj.springboot.service.TbUserService;
import com.clj.springboot.util.ExcelUtil;
import com.clj.springboot.util.Msg;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class UserController {

//    @Autowired
//    private TbUserMapper tbUserMapper;

    @Autowired
    private TbUserService tbUserService;

//
//    @RequestMapping("hello")
//    public Object hello(@RequestParam(name="pageSize",defaultValue = "2") Integer pageSize,
//                        @RequestParam(name="pageNum",defaultValue = "1") Integer pageNum ){
//        PageHelper.startPage(pageNum,pageSize);
//      List<TbUser> list =  tbUserMapper.selectAll();
//        PageInfo<TbUser> info = new PageInfo<>(list);
//        return info;
//    }

    @RequestMapping("selectById")
   public String aopselectById(HttpServletRequest request, HttpServletResponse response){
        UploadExcel ue = new UploadExcel();
        List<TbUser> orderList = tbUserService.selectAll();
        ue.setTitle(TbUser.getProperty());
        ue.setContent(TbUser.getcontent(orderList));
        ue.setSheetName("订单信息表");
        ue.setCellRangeAddress(TbUser.getRepeatNumber(orderList));
        ue.setMergeRow(null);
        String fileName = ue.getSheetName() + System.currentTimeMillis()+".xls";
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(ue.getSheetName(), ue.getTitle(), ue.getContent(), null, ue.getCellRangeAddress(), ue.getMergeRow());
        try {
            this.setResponseHeader(response, fileName);
            //FileOutputStream os = new FileOutputStream("E:/"+fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
    		
    		/*InputStream fileContent =new FileInputStream("E:/"+fileName);
    		 //InputStream fileContent = fi.getInputStream();
			 fileName = "E:/"+fileName;  
	         String key = AliOssUtil.uploadFile(fileContent, "mall",fileName);  
	         OSSConfigure ossConfigure = new OSSConfigure("oss.properties");  
	         apkUrl=ossConfigure.getAccessUrl()+"/"+key;*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
   }
    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   class deliver implements Runnable{

       @Override
       public void run() {
           System.out.println(1);
           System.out.println(2);
           System.out.println(3);
           System.out.println(4);

       }
   }
}
