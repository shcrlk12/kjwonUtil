package util;

public enum FTPDownloadList {
	CFC0("cfc0"), CFC1("cfc0_1"), NVRAM0("nvram0"), NVRMA1("nvram1");
	
	private String remotePath;
	
	private FTPDownloadList(String remotePath) {
		this.remotePath = remotePath;
	}
	
	public String getName() {
		return remotePath;
	}
}
