package cn.market.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.market.bean.Goods;
import cn.market.dao.UserDao;
import cn.market.util.Result;

@Service
@Transactional
public class FileService {
		
	@Autowired
	private UserDao dao;
	/*
	 * ��ȡ��������,���������ݿ⣬�ٻ�ȡ
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
	       
	            //�����ļ�
	            File excel = new File(newFile.getAbsolutePath());
	            if (excel.isFile() && excel.exists()) {   //�ж��ļ��Ƿ����

	                String[] split = excel.getName().split("\\.");  //.�������ַ�����Ҫת�壡��������
	                Workbook wb;
	                //�����ļ���׺��xls/xlsx�������ж�
	                if ( "xls".equals(split[1])){
	                    FileInputStream fis = new FileInputStream(excel);   //�ļ�������
	                    wb = new HSSFWorkbook(fis);
	                }else if ("xlsx".equals(split[1])){
	                    wb = new XSSFWorkbook(excel);
	                }else {
	                
	                    result.setCode(500);
	        			result.setSuccess(false);
	        			result.setMessage("�ļ����ʹ���");
	        			return result;
	                }

	                //��ʼ����
	                Sheet sheet = wb.getSheetAt(0);     //��ȡsheet 0

	                int firstRowIndex = sheet.getFirstRowNum()+1;   //��һ�������������Բ���
	                int lastRowIndex = sheet.getLastRowNum();

	                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //������
	    
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
                            		System.out.println("�����"+i.getGoodname()+row.getCell(1).toString());
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
                                 	result.setMessage("������Ʒʧ��");
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
	    			result.setMessage("�Ҳ���ָ���ļ�");
	    			return result;
	            }
	            result.setCode(1);
    			result.setMessage("�ϴ��ļ��ɹ�");
    			return result; 
	        
	        }
	        catch (Exception e){
	            e.printStackTrace();
	            result.setCode(500);
    			result.setSuccess(false);
    			result.setMessage("ʧ��");
    			return result;
	        }
	   

		
		
		
		
	}
}
