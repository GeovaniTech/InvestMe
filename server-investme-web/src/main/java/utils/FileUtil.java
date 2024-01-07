package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.model.file.UploadedFile;

public class FileUtil {
	public static byte[] getBytesFromPrimefacesFile(UploadedFile primefacesFile) throws IOException {
		byte[] byteFile = toByteArrayUsingJava(primefacesFile.getInputStream());
		
		return byteFile;
	}
	
	public static byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int reads = is.read();
		
		while(reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		
		return baos.toByteArray();
	}

}
