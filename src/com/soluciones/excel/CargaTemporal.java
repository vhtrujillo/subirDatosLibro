package com.soluciones.excel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class CargaPermanente
 */
@WebServlet("/CargaPermanente")
public class CargaTemporal extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargaTemporal() 
    {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if(!isMultipartContent)
		{
			out.println("No estas cargando nada");
			return;
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try 
		{
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			
			if(!it.hasNext())
			{
				out.println("No se cargará nada");
				return;
			}
			
			out.println("<h3>Se cargaron solo los siguientes archivos</h3>");
			out.println("<table border=\"1\">");
			out.println("<tr><th>Nombre del archivo</th><th>Tamaño del archivo</th></tr>");
			while(it.hasNext())
			{
				
				FileItem file = it.next();
				boolean isFormField = file.isFormField();
				if(!isFormField)
				{
					if(!file.getName().isEmpty())
					{
						out.println("<tr><td>" + file.getName() + "</td><td>" + file.getSize() + " bytes</td></tr>");
					}
				}
			}
			
			out.println("</table>");
		} 
		catch (FileUploadException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
