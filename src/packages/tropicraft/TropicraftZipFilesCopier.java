package tropicraft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import CoroUtil.util.CoroUtilFile;

public class TropicraftZipFilesCopier {
	
	public static void init() {
		copyFile("/assets/" + Tropicraft.modID + "/schematics/", "TradeHut.schematic");
    }
	
	public static boolean copyFile(String filePath, String fileName) {
		InputStream is = null;
		OutputStream os = null;
		try {

			//make folders ahead of time
			String root = CoroUtilFile.getSaveFolderPath();
			File dirSchem = new File(root + filePath);
			if (!dirSchem.exists()) dirSchem.mkdirs();
			
			URL urlPath = TropicraftZipFilesCopier.class.getResource(filePath + fileName);
			is = TropicraftZipFilesCopier.class.getResourceAsStream(filePath + fileName);
			os = new FileOutputStream(new File(root + File.separator + filePath + fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
		} catch (Exception ex) { ex.printStackTrace(); } finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					// outputStream.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
			}
		}
		
		
		return false;
	}
}
