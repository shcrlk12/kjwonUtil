package util;

import java.io.File;

public class FileManager {
	public static void folderDelete(String folderPath) throws Exception{
		File file = new File(folderPath);
		
		if(!file.isDirectory()) {
			throw new Exception(folderPath + " 는 디렉토리가 아닙니다.");
		}
		File[] files = file.listFiles();
		
		for(File f : files) {
			if(f.isDirectory())
			{
				String localPath = folderPath + "\\" + f.getName();
				folderDelete(localPath);
			}
			else {
				if(!f.delete())
					throw new Exception(folderPath + " 제거 실패");
			}
		}
		file.delete();
	}
}
