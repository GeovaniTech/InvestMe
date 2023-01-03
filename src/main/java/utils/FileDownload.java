package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import to.TOFile;

public class FileDownload {
	public static void download(TOFile file) throws IOException {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setContentType("application/octet-stream");
		response.setContentLength(file.getBytes().length);
		response.getOutputStream().write(file.getBytes());
		response.flushBuffer();
		
		FacesContext.getCurrentInstance().responseComplete();
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
