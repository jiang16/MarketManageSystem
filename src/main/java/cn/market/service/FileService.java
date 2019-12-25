package cn.market.service;

import cn.market.bean.Goods;
import cn.market.dao.UserDao;
import cn.market.util.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class FileService {
		
	@Autowired
	private UserDao dao;
	/*
	 * 获取表中数据,并存入数据库，再获取
	 */
	public Result getFileDate(MultipartFile file) {
		    Result result = new Result();
		    result.setSuccess(true);
		    String fileName = file.getOriginalFilename();
	        String suffix = fileName.substring(fileName.lastIndexOf('.'));
	        String newFileName = new Date().getTime() + suffix;
	        String path = "E:/test/";
	        File newFile = new File(path + newFileName);
	        try {
	            file.transferTo(newFile);
	       
	            //解析文件
	            File excel = new File(newFile.getAbsolutePath());
	            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

	                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
	                Workbook wb;
	                //根据文件后缀（xls/xlsx）进行判断
	                if ( "xls".equals(split[1])){
	                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
	                    wb = new HSSFWorkbook(fis);
	                }else if ("xlsx".equals(split[1])){
	                    wb = new XSSFWorkbook(excel);
	                }else {
	                
	                    result.setCode(500);
	        			result.setSuccess(false);
	        			result.setMessage("文件类型错误");
	        			return result;
	                }

	                //开始解析
	                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

	                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
	                int lastRowIndex = sheet.getLastRowNum();

	                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
	    
	                    Row row = sheet.getRow(rIndex);
	                    if (row != null) {
	                      /*  int firstCellIndex = row.getFirstCellNum();
	                        int lastCellIndex = row.getLastCellNum();*/
	                        Goods goods = new Goods();
	                        goods.setGoodid(row.getCell(0).toString());
                            goods.setGoodname(row.getCell(1).toString());
                            goods.setStock(row.getCell(2).toString());
                            goods.setPrice(row.getCell(3).toString());
                            goods.setMemberprice(row.getCell(4).toString());
                            goods.setCategory(row.getCell(5).toString());
                            goods.setSupplier(row.getCell(6).toString());
                            goods.setSupplierphone(row.getCell(7).toString());
                            goods.setSupplieraddress(row.getCell(8).toString());
                            List<Goods> list = dao.getGoodsData();
                            String flagStock = null;
                            int flag = 1;
                            for(Goods i:list) {
             
                            	if(i.getGoodname().equals(row.getCell(1).toString())) {
                            		System.out.println("有相等"+i.getGoodname()+row.getCell(1).toString());
                            	    flag = 0;
                            		for(int j=0;j<i.getStock().length();j++) {
                            			if((i.getStock().charAt(j)<'0'||i.getStock().charAt(j)>'9')&&(i.getStock().charAt(j)!='.')) {
                            				flagStock= String.valueOf(i.getStock().charAt(j));
                            				break;
                            			}
                            		}
                            		String[] old = i.getStock().split(flagStock);
                            		String[] ne = row.getCell(2).toString().split(flagStock);
                            		String count = String.valueOf(Integer.parseInt(old[0])+Integer.parseInt(ne[0]));
                            		String newstock = count+flagStock;
                                    System.out.println(newstock);
                            		i.setStock(newstock);
                            		dao.updateGood(newstock,i.getGoodname());
                            		
                            		
                            	}
                            }
                          
                            if(flag==1) {
                            	
                            	 try {
                                 	dao.insertGoods(goods);
                                 }
                                 catch(Exception e) {
                                 	e.printStackTrace();
                                 	result.setCode(500);
                                 	result.setMessage("插入商品失败");
                                 	result.setSuccess(false);
                                 	return result;
                                 }
     	    
                            }
                            
                           
	                        }
	                    }
	                }
	                
	               
	           
	            else {
	            	result.setCode(500);
	    			result.setSuccess(false);
	    			result.setMessage("找不到指定文件");
	    			return result;
	            }
	            result.setCode(1);
    			result.setMessage("上传文件成功");
    			return result; 
	        
	        }
	        catch (Exception e){
	            e.printStackTrace();
	            result.setCode(500);
    			result.setSuccess(false);
    			result.setMessage("失败");
    			return result;
	        }
	   

		
		
		
		
	}
}
