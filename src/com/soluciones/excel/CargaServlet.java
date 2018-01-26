package com.soluciones.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Servlet implementation class CargaServlet
 */
@WebServlet("/CargaServlet")
@MultipartConfig
public class CargaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String []titulo=new String [100];
	int cont =0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   public static  List<String> datos = new ArrayList<String>();
    

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
	
		// constructs path of the directory to save uploaded file

		
		Part filepart = request.getPart("file1");
		
		if(filepart != null)
		{
			datos.clear();
			
			if(datos.isEmpty() || datos.size() <= 0)
			{
				//Part filepart = request.getPart("file1");
				
				FileInputStream input = (FileInputStream)filepart.getInputStream();
				
				try
				{
					Workbook libro = WorkbookFactory.create(input);
					
					Sheet hoja = libro.getSheetAt(0);
					out.println("<form name=\"form1\" id=\"form1\" action=\"Temporal\" method=\"post\" enctype=\"multipart/form-data\">");
					out.println("<table align=\"center\" border=\"1\" style=\"width:100%\">");
					out.println("<tr>");
					
					for(Row fila : hoja)
					{
						for(Cell celda : fila)
						{
							if(fila.getRowNum() == 0)
							{
								out.println("<th>" + celda.getRichStringCellValue().getString() + "</th>");
							}
							
							else
							{
								if(celda.getColumnIndex() == 6)
								{
									datos.add(celda.getRichStringCellValue().toString());
								}
							}
						}
					}
					out.println("<th>Cargar</th>");
					out.println("</tr>");
					
					
					for(Row fila : hoja)
					{
						out.println("<tr>");
						for(Cell celda : fila)
						{
							if(fila.getRowNum() > 0)
							{
								out.println("<td>" + celda.getRichStringCellValue().getString() + "</td>");
							}
						}
						
						
						out.println("</tr>");
					}
					out.println("<tr><td align=\""
							+ "right\" colspan=\"8\">Enviar: <input type=\"submit\" name=\"finalizar\" value=\"Finalizar\"></td></tr>");
					out.println("</table>");
					out.println("</form>");
					out.println("<form action=\"FileUploadServlet\" method=\"post\"\r\n" + 
							"		enctype=\"multipart/form-data\">\r\n" + 
							"		Seleccionar archivos:<input type=\"file\" accept=\".png, .jpg, .jpeg, .pdf, .mp4, .mp3\" name=\"fileName\" multiple=\"multiple\"> <br>\r\n" + 
							"		<input type=\"submit\" value=\"Subir\">\r\n" + 
							"	</form>");
					
					input.close();
					
					for(String a : datos)
				    {
					 out.println("<p>"+ a +"</p>");
					
					}
				}
				catch (EncryptedDocumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvalidFormatException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
}
