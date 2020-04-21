package com.nautestech.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;



public class Command {
	
	
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
		String format = "";
		String runFile = "";
		
		if(isMxxMode.equals("0")) { //wav
			format = "wav";
			runFile = "./mReaderBin/mReader0";
		} else if(isMxxMode.equals("1")) { //mp3
			format = "mp3";
			runFile = "";
		} else if(isMxxMode.equals("2")) { //wav.enc
			format = "wav";
			runFile = "mxx2wav";
		} else if(isMxxMode.equals("3")) { //mp3.enc
			format = "mp3";
			runFile = "mxx2mp3";
		}
		
		createCommand = "cd /home/recording/bin; "+runFile+".sh \"" + dirName + FileName.replace("wav", "mxx").replace("mp3", "mxx") + "\" \"" + dirName + FileName.replace("mxx", format)+"\"";
		
		runCommand(createCommand);
	}
	 
	
	public void dencMp3(String FileName, String dirName, String mode) throws Exception 
	{
		String format = "";
		if(mode.equals("0")) { //wav
			format = "wav";
		} else if(mode.equals("1")) { //mp3
			format = "mp3";
		} else if(mode.equals("2")) { //wav.enc
			format = "wav";
		} else if(mode.equals("3")) { //mp3.enc
			format = "mp3";
		}
		
		String createCommand = "cd /home/recording/bin; ./dencfile.sh \""+dirName+File.separator+FileName.replace("mxx",format)+"\"";
		
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
