/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
import Entities.Produit;
import utils.DataSource;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author asus
 */
public class Pdf {
        private Connection con;
        private Statement ste;
    public Pdf()  {
        con = DataSource.getInstance().getCnx();
          
    
}
    public void add(String file) throws FileNotFoundException, SQLException, DocumentException{
        
                Document my_pdf_report = new Document();
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream(file));
                my_pdf_report.open();            
                //we have four columns in our table
                PdfPTable my_report_table = new PdfPTable(2);
                //create a cell object
                PdfPCell table_cell;
            con = DataSource.getInstance().getCnx();
            ste = con.createStatement();
            ResultSet res = ste.executeQuery("select * from categorie");
            while(res.next()){
                table_cell=new PdfPCell(new Phrase("Nom"));
                my_report_table.addCell(table_cell);
                table_cell=new PdfPCell(new Phrase(res.getString(2)));
                my_report_table.addCell(table_cell);
            }

                               
                                
                /* Attach report table to PDF */
                my_pdf_report.add(my_report_table);                       
                my_pdf_report.close();
                
               /* Close all DB related objects */

        
    }
     
}
