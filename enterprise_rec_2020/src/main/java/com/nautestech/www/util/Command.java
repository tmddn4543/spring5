package com.nautestech.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;



public class Command {

	
	
	public static Key getAESKey() throws Exception {
        String iv;
        Key keySpec;
 
        String key = "@Nautes123@!@#$@";
        iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
 
        int len = b.length;
        if (len > keyBytes.length) {
           len = keyBytes.length;
        }
 
        System.arraycopy(b, 0, keyBytes, 0, len);
        keySpec = new SecretKeySpec(keyBytes, "AES");
 
        return keySpec;
    }
	
	// 복호화
	public String decAES(String enStr) throws Exception {
		Key keySpec = getAESKey();
		String iv = "@Nautes123@!@#$@";
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
		byte[] byteStr = Base64.getDecoder().decode(enStr.getBytes("UTF-8"));
		String decStr = new String(c.doFinal(byteStr), "UTF-8");
		
		return decStr;
	}
	
	public void chkFolder(String dirName) {
		File checkF = new File(dirName);
		
		if(!checkF.exists()) {
			String createCommand = "mkdir -p "+dirName+File.separator;
			
			runCommand(createCommand);
		}
	}
	
	
	
	
	
	
	public void CopyMXX(String FileName, String dirName) throws Exception 
	{
		String createCommand = "";
		String command	=	"";
		
		String	mxxFileDate	=	FileName.substring(0, 8);
		String	mxxFileDateTime	=	FileName.substring(0, 10);
		
		//if (prop.getServerType().equals(ServerType.StandAloneServerType)) {
			command = "cp ";
		//} 
		String ftpNum = FileName.substring(FileName.indexOf("_0000") + 5, FileName.indexOf("_0000") + 6);
		
		String sourcePath = "/ftp/" + mxxFileDate + "/" + mxxFileDateTime + "/" + FileName.replace("wav", "mxx").replace("mp3", "mxx");
		String destPath = dirName + FileName.replace("wav", "mxx").replace("mp3", "mxx");
		createCommand = command + sourcePath + " " + destPath;
		runCommand(createCommand);
	}
	
	
	
	
	public void ConvertMXX(String FileName, String dirName,String isMxxMode) throws Exception 
	{
		String createCommand = "";
		String runFile = "";
		
		if(isMxxMode.equals("wav")) { //wav
			runFile = "./mReaderBin/mReader0";
		} else if(isMxxMode.equals("mp3")) { //mp3
			runFile = "";
		} else if(isMxxMode.equals("mp3")) { //wav.enc
			runFile = "mxx2wav";
		} else if(isMxxMode.equals("3")) { //mp3.enc
			runFile = "mxx2mp3";
		}
		
		createCommand = "cd /home/recording/bin; "+runFile+".sh \"" + dirName + FileName.replace("wav", "mxx").replace("mp3", "mxx") + "\" \"" + dirName + FileName.replace("mxx", isMxxMode)+"\"";
		
		runCommand(createCommand);
	}
	 
	
	public void dencMp3(String FileName, String dirName, String isMxxMode) throws Exception 
	{
		
		String createCommand = "cd /home/recording/bin; ./dencfile.sh \""+dirName+File.separator+FileName.replace("mxx",isMxxMode)+"\"";
		
		runCommand(createCommand);
	}
	
	private String runCommand(String createCommand) {
		System.out.println(createCommand);
		String result = "";
		String[] command = { "/bin/sh", "-c", createCommand };
		System.out.println("/bin/sh -c " + createCommand);
		Process p = null;
		Runtime runtime = null;

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader buf = null; 
		
		try {
			runtime = Runtime.getRuntime();;
			p = runtime.exec(command);
			is = p.getInputStream();
			isr = new InputStreamReader(is);
			buf = new BufferedReader(isr);
			
			String line;
			while((line=buf.readLine())!=null) {
				result = line;
			}
			buf.close();
			isr.close();
			is.close();
		} catch(Exception e){
			System.err.println(e.toString());
		} finally {
			try { if(runtime != null) runtime = null; } catch(Exception e) {}
			try { if(p != null) p.destroy(); p = null; } catch(Exception e) {}
			try { if(buf != null) buf.close(); buf = null; } catch(Exception e) {}
			try { if(isr != null) isr.close(); isr = null; } catch(Exception e) {}
			try { if(is != null) is.close(); is = null; } catch(Exception e) {}
		}
		System.out.println(result);
		return result;
	}
}
