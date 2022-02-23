package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressor {
	public static ZipOutputStream zos = null;

	/** 
	 * 폴더내 모든 폴더 및 파일 압축
	 * @param dir 
	 * @param zipName 
	 * @param zipPath
	 * @throws IOException 
	 * */
	public static void makeZipFromDir(String dir, Object zipName, Object zipPath) throws IOException {
		File directory = new File(dir + File.separator);

		if (!directory.isDirectory()) {
			new IOException(dir + ": 폴더가 아님");
		}
		if (zipPath != null) {
			zipPath += File.separator;
		} else {
			zos = new ZipOutputStream(new FileOutputStream(directory.getParent() + "\\" + zipName));
			System.out.println(dir + " folder 압축 중...");
		}

		File[] fileList = directory.listFiles();
		byte[] buf = new byte[1024];

		FileInputStream in = null;

		for (File file : fileList) {
			if (file.isDirectory()) {
				makeZipFromDir(
						dir + "\\" + file.getName(),
						null,
						(zipPath == null ? "" : zipPath) + file.getName());
			} else {
				in = new FileInputStream(file.getAbsoluteFile());
				zos.putNextEntry(new ZipEntry(zipPath + file.getName()));

				int len;

				while ((len = in.read(buf)) > 0) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
		}
		if (zipPath == null) {
			zos.close();
			System.out.println(dir + " folder 압축 완료");
		}
	}

	/**
	 * * 파일 압축 * @param dir * @param fileName * @param zipName * @throws IOException
	 */
	public static void makeZip(String dir, String fileName, String zipName) throws IOException {
		System.out.print("start:" + dir + File.separator + fileName + "-> " + zipName);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dir + zipName));
		byte[] buf = new byte[1024];
		FileInputStream in = new FileInputStream(dir + File.separator + fileName);
		zos.putNextEntry(new ZipEntry(fileName));
		int len;
		while ((len = in.read(buf)) > 0) {
			zos.write(buf, 0, len);
		}
		zos.closeEntry();
		in.close();
		zos.close();
		System.out.println("... end");
	}
}
