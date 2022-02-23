package util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;


public class FTPDownloader {
	FTPClient ftpClient = null;
	File f = new File("C:\\bachmann\\1_Git Repository\\eclipse\\contents\\workspace\\data-sender\\cfc0");
	FileOutputStream fos = null;
	
	public FTPDownloader(String host, String user, String pwd) throws Exception {
		ftpClient = new FTPClient();
		ftpClient.setDefaultPort(21);
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;
		ftpClient.connect(host);// 호스트 연결
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		boolean result = ftpClient.login(user, pwd);// 로그인
		System.out.println(result ? "로그인 성공" : "로그인 실패");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		ftpClient.changeWorkingDirectory("..");
	}
	
	public static FTPClient initializeFTPClient(String host, String user, String pwd) throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setDefaultPort(21);
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		ftpClient.connect(host);// 호스트 연결
		
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		
		if(!ftpClient.login(user, pwd)) {
			ftpClient.disconnect();
			throw new Exception("FTP Server Login Fails");
		}
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		ftpClient.changeWorkingDirectory("..");
		
		return ftpClient;
	}
	public void downloadFile() throws Exception {

			fos = new FileOutputStream(f);
			boolean isSuccess = ftpClient.retrieveFile("cfc0", fos);
			if(isSuccess)
				System.out.println("성공");
			else
				System.out.println("실패");
	}

	public static void disconnect(FTPClient ftpClient) {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException f) {
				f.printStackTrace();
			}
		}
	}
	
	public static void downloadFolder(
		    FTPClient ftpClient, String remotePath, String localPath) throws IOException
		{
		    System.out.println("Downloading folder " + remotePath + " to " + localPath);

		    FTPFile[] remoteFiles = ftpClient.listFiles(remotePath);

		    new File(localPath).mkdirs();
		    
		    for (FTPFile remoteFile : remoteFiles)
		    {
		        if (!remoteFile.getName().equals(".") && !remoteFile.getName().equals(".."))
		        {
		            String remoteFilePath = remotePath + "\\" + remoteFile.getName();
		            String localFilePath = localPath + "\\" + remoteFile.getName();

		            if (remoteFile.isDirectory())
		            {
		                downloadFolder(ftpClient, remoteFilePath, localFilePath);
		            }
		            else
		            {
		                System.out.println("Downloading file " + remoteFilePath + " to " +
		                    localFilePath);
                
		                OutputStream outputStream =
		                    new BufferedOutputStream(new FileOutputStream(localFilePath));
		                if (!ftpClient.retrieveFile(remoteFilePath, outputStream))
		                {
		                    System.out.println("Failed to download file " + remoteFilePath);
		                }
		                outputStream.close();
		            }
		        }
		    }
		}
}
