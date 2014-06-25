/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arnellconsulting.worktajm.rest.controllers;
//
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.data.JRXmlDataSource;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.io.StringReader;

/**
 *
 * @author jiar
 */
class ReportController {

//    public RunReportResponseType runReport(@WebParam(name="request") RunReportRequestType request) {
//        // Instantiate response object
//        RunReportResponseType response = new RunReportResponseType();
//
//        // Temp Read image
///*
// *      try {
//            byte[] jpgImage = getBytesFromFile(new File("D:/workspace/prince1999JPG.jpg"));
//            int lengte = jpgImage.length;
//        }  catch(IOException ioe) {
//            System.out.println("Exception while reading the file" + ioe);
//        }
//*/
//        // Read report template
//        File file = new File(request.getRapportLocatie() + request.getRapportNaam());
//        FileInputStream template = null;
//        try {
//            template = new FileInputStream(file);
//        } catch(FileNotFoundException e) {
//            System.out.println("File " + file.getAbsolutePath() +
//            " could not be found on filesystem");
//        }  catch(IOException ioe) {
//            System.out.println("Exception while reading the file" + ioe);
//        }
//
//        // Load report template
//        JasperDesign design = null;
//        try {
//            design = JRXmlLoader.load(template);
//        } catch (JRException e) { 
//            e.printStackTrace();
//        }
//        
//        // Compile report template
//        JasperReport report = null;
//        try {
//            report = JasperCompileManager.compileReport(design);
//        } catch (JRException e) { 
//            e.printStackTrace();
//        }
//        
//        // parse XML payload.
//        Document xmlDoc = null;
//        JRXmlDataSource  ds = null;
//        try {
//            String xmlPayload = request.getXmlData().trim().replaceFirst("&lt;version/&gt;", "").replaceFirst("<version/>", "");
//            xmlDoc = convertStringToDocument(xmlPayload); 
//            ds = new JRXmlDataSource(xmlDoc,report.getQuery().getText());
//        } catch (JRException e) { 
//            e.printStackTrace();
//        }
//
//        // Fill report & create PDF
//        JasperPrint print = null;
//        byte[] pdf = null;      
//        try {
//            print = JasperFillManager.fillReport(report, null, ds);
//            pdf = JasperExportManager.exportReportToPdf(print);
//        } catch (JRException e) { 
//            e.printStackTrace();
//        }
//
//        // Fill return object
//        response.setContentSize(String.valueOf(pdf.length));
//        response.setContentType("application/pdf");
//        response.setOutputNaam(request.getOutputNaam());
//        response.setOutputLocatie(request.getOutputLocatie());
//        response.setReport(pdf);
//
//        if (request.getOutputNaam().equals("") == false) {
//            // Create output file.
//            OutputStream output = null;
//            try {
//                output = new FileOutputStream(new File(request.getOutputLocatie() + request.getOutputNaam()));
//            } catch (FileNotFoundException e) { e.printStackTrace();
//            }
//    
//            // Write PDF to output file
//            try {
//                JasperExportManager.exportReportToPdfStream(print, output);
//            } catch (JRException e) { e.printStackTrace();
//            }
//        }
//        return response;
//    }   
//   
}
