package cn.market.service;

import cn.market.bean.Goods;
import cn.market.dao.GoodsDao;
import cn.market.dao.UserDao;
import cn.market.util.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class FileService {
		
	@Autowired
	private GoodsDao dao;
	/*
	 * 获取表中数据,并存入数据库，再获取
	 */
	public Result getFileDate(MultipartFile file) {
		    Result result = new Result();
		    result.setSuccess(true);
		    String fileName = file.getOriginalFilename();
	        String suffix = fileName.substring(fileName.lastIndexOf('.'));
	        String newFileName = new Date().getTime() + suffix;

		Resource resource = new ClassPathResource("");
		String path = null;
		try {
			path = resource.getFile().getAbsolutePath();
			System.out.println(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	                        if (row.getCell(0) == null) {
	                        	break;
							};
	                        goods.setGoodid(getStringCellValue(row.getCell(0)));
							goods.setGoodname(row.getCell(1).toString());
                            goods.setStock(row.getCell(2).toString());
                            goods.setPrice(row.getCell(3).toString());
                            goods.setMemberprice(row.getCell(4).toString());
                            goods.setCategory(row.getCell(5).toString());
                            goods.setSupplier(row.getCell(6).toString());
                            goods.setSupplierphone(getStringCellValue(row.getCell(7)));
                            goods.setSupplieraddress(row.getCell(8).toString());
                            Goods oldGoods = dao.selectSupplierInfo(goods.getGoodid());
                            if (oldGoods != null) {
                            	dao.update(goods);
							}else {
                            	dao.insertGoods(goods);
							}
//                            List<Goods> list = dao.getGoodsData();
//                            String flagStock = null;
//                            int flag = 1;
//                            for(Goods i:list) {
//
//                            	if(i.getGoodname().equals(row.getCell(1).toString())) {
//                            		System.out.println("有相等"+i.getGoodname()+row.getCell(1).toString());
//                            	    flag = 0;
//                            		for(int j=0;j<i.getStock().length();j++) {
//                            			if((i.getStock().charAt(j)<'0'||i.getStock().charAt(j)>'9')&&(i.getStock().charAt(j)!='.')) {
//                            				flagStock= String.valueOf(i.getStock().charAt(j));
//                            				break;
//                            			}
//                            		}
//                            		String[] old = i.getStock().split(flagStock);
//                            		String[] ne = row.getCell(2).toString().split(flagStock);
//                            		String count = String.valueOf(Integer.parseInt(old[0])+Integer.parseInt(ne[0]));
//                            		String newstock = count+flagStock;
//                                    System.out.println(newstock);
//                            		i.setStock(newstock);
//                            		dao.updateGood(newstock,i.getGoodname());
//
//
//                            	}
//                            }
//
//                            if(flag==1) {
//
//                            	 try {
////                                 	dao.insertGoods(goods);
//                                 }
//                                 catch(Exception e) {
//                                 	e.printStackTrace();
//                                 	result.setCode(500);
//                                 	result.setMessage("插入商品失败");
//                                 	result.setSuccess(false);
//                                 	return result;
//                                 }
//
//                            }
                            
                           
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
    			result.setMessage("上传失败"+e.getMessage());
    			return result;
	        }
	   

		
		
		
		
	}
	private String getStringCellValue(Cell cell) //获取excel单元格数据内容中为字符串类型的数据
	{
		String strCell = "";
		if(cell == null)
		{
			return "";

		}else
			//判断数据的类型
			switch (cell.getCellType()){
				case Cell.CELL_TYPE_NUMERIC: //数字
					DataFormatter dataFormatter = new DataFormatter();

					strCell = dataFormatter.formatCellValue(cell);
					//这行代码就可以完美解决数字的小数点显示问题及电话号等长数字的科学计数法显示问题
					//注意不要使用HSSFDataFormatter方法，这只支持2003版的excel哦
					break;
				case Cell.CELL_TYPE_STRING: //字符串
					strCell = String.valueOf(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN: //Boolean
					strCell = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA: //公式
					strCell = String.valueOf(cell.getCellFormula());
					break;
				case Cell.CELL_TYPE_BLANK: //空值
					strCell = "";
					break;
				case Cell.CELL_TYPE_ERROR: //故障
					strCell = "非法字符";
					break;
				default:
					strCell = "未知类型";
					break;
			}
		if(strCell.equals("")||strCell==null)
		{
			return "";
		}


		return strCell;

	}
}
