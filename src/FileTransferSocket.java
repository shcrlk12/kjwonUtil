package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class FileTransferSocket {
	private Socket socket = null;
	
	public FileTransferSocket(Socket socket){
		this.socket = socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void fileTransfer(String filePath, int bufferSize) throws Exception{

		InputStream inputStream =  new BufferedInputStream(new FileInputStream(filePath));

		/*file read and send data to client*/
		byte[] byteBuff = new byte[bufferSize];
		OutputStream outputStream =  new BufferedOutputStream(socket.getOutputStream());

		long start = System.currentTimeMillis();

		int readLength;
		
		while((readLength = inputStream.read(byteBuff)) != -1)
		{
			outputStream.write(byteBuff, 0, readLength);
			outputStream.flush();
		}
			
		long end = System.currentTimeMillis();
		
		System.out.println("½Ã°£: " + (end-start) + "ms");
		
		inputStream.close();
		outputStream.close();
	}
}
