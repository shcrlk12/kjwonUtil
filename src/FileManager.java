package util;

import java.io.File;

public class FileManager {
	public static void folderDelete(String folderPath) throws Exception{
		File file = new File(folderPath);
		
		if(!file.isDirectory()) {
			throw new Exception(folderPath + " �� ���丮�� �ƴմϴ�.");
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
					throw new Exception(folderPath + " ���� ����");
			}
		}
		file.delete();
	}
}
