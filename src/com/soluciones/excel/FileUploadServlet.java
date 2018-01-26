package com.soluciones.excel;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1000, // 0 MB
		maxFileSize = 1024 * 1024 * 500, // 0 MB
		maxRequestSize = 1024 * 1024 * 1000)
public class FileUploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 205242440643911308L;
	CargaServlet c = new CargaServlet();
	/**
	 * Directory where uploaded files will be saved, its relative to the web
	 * application directory.
	 */
	private static final String UPLOAD_DIR = "uploads";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists())
		{
			fileSaveDir.mkdirs();
		}
	
		String datos = "", status = "";
		System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());

		String fileName = null;
		// Get all the parts from request and write it to the file on server
		for (Part part : request.getParts())
		{
			fileName = getFileName(part);
			part.write(uploadFilePath + File.separator + fileName);
            
			for (int i = 0; i < c.titulo.length; i++)
			{
				System.out.println( ""+c.titulo[i]);
				if (c.titulo[i] != fileName)
				{
					
					status = "--No existe archivo en elxcel";
				} 
				else
				{
					status = "--Si existe archivo en elxcel";
				}
			}
			
			datos = datos+ fileName + " " + status + "<br><br> <br>";
		}

		request.setAttribute("message",
				"<h2>Los siguientes archivos fueron subidos Correctamente</h2><br>" + "<h4>" + datos + "</h4>");
		getServletContext().getRequestDispatcher("/response.jsp").forward(request, response);
	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part)
	{
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens)
		{
			if (token.trim().startsWith("filename"))
			{
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}
}
