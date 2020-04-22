package com.nautestech.www.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ZipDownload {

	
	public void down(HttpServletRequest request, HttpServletResponse response,String[] sp_arr) {
		String day = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
		
		//다운받을위치
		String zipFile = "/home/zip/call_history_"+day+".zip";
		String downloadFileName = "call_history_"+day;
		List<String> sourceFiles = new ArrayList<String>();
		
		//파일위치+파일이름
		//경로와파일이름적는것
		for(int i=0; i<sp_arr.length; i++){
			sourceFiles.add(sp_arr[i]);
		}
		try{
		    // ZipOutputStream을 FileOutputStream 으로 감쌈
		    FileOutputStream fout = new FileOutputStream(zipFile);
		    ZipOutputStream zout = new ZipOutputStream(fout);
		    for(int i=0; i < sourceFiles.size(); i++){

		        //본래 파일명 유지, 경로제외 파일압축을 위해 new File로 
		        ZipEntry zipEntry = new ZipEntry(new File(sourceFiles.get(i)).getName());
		        zout.putNextEntry(zipEntry);

		        //경로포함 압축
		        //zout.putNextEntry(new ZipEntry(sourceFiles.get(i)));

		        FileInputStream fin = new FileInputStream(sourceFiles.get(i));
		        byte[] buffer = new byte[1024];
		        int length;

		        // input file을 1024바이트로 읽음, zip stream에 읽은 바이트를 씀
		        while((length = fin.read(buffer)) > 0){
		            zout.write(buffer, 0, length);
		        }

		        zout.closeEntry();
		        fin.close();
		    }

		    zout.close();

		    response.setContentType("application/zip");
		    response.addHeader("Content-Disposition", "attachment; filename=" + downloadFileName + ".zip");
 
		    FileInputStream fis=new FileInputStream(zipFile);
		    BufferedInputStream bis=new BufferedInputStream(fis);
		    ServletOutputStream so=response.getOutputStream();
		    BufferedOutputStream bos=new BufferedOutputStream(so);

		    byte[] data=new byte[2048];
		    int input=0;

		    while((input=bis.read(data))!=-1){
		        bos.write(data,0,input);
		        bos.flush();
		    }

		    if(bos!=null) bos.close();
		    if(bis!=null) bis.close();
		    if(so!=null) so.close();
		    if(fis!=null) fis.close();

		    } catch(IOException ioe){System.out.println(ioe); }
	}
}
