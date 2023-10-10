package com.example.techmasterpi.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.techmasterpi.domain.Reclamation;
import com.example.techmasterpi.repos.ReclamationRepo;
import com.example.techmasterpi.repos.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.techmasterpi.domain.ContractPlan;
import com.example.techmasterpi.domain.Payment;
import com.example.techmasterpi.model.ContractPlanDTO;
import com.example.techmasterpi.util.NotFoundException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import com.example.techmasterpi.domain.Plan;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.repos.ContractPlanRepository;
import com.example.techmasterpi.repos.PlanRepository;
import com.example.techmasterpi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class ServiceReclamation implements IServiceReclamation {
@Autowired
	ReclamationRepo reclamationRepo;
@Override
	public void addReclamtion(Reclamation reclamation){
		reclamationRepo.save(reclamation);
	}

	public void exportcontrat(int idReclamation, String filePath) throws IOException {
		try {
			Reclamation reclamation = reclamationRepo.findById(idReclamation).get();

			String htmlContent = "<!DOCTYPE html> \n" +
					"<html>\n" +
					"    <head><style>body{background-color: rgb(156, 155, 154);}h1{text-align: center ;font-weight: bold;color: rgb(34, 33, 33);font-family: cursive;}p{text-align: center;font-size: medium;} </style></head>\n" +
					"    <body>\n" +
					"      <h1>Tech Master</h1> \n \n" +
					"      \n" +
					"      <p>Date Reclamation: "+ reclamation.getCreationDate() +"</p>\n" +
					"      <p>Object: "+reclamation.getObjet() +"</p>\n" +
					"      <p>Message: "+ reclamation.getMessage()+" </p>\n" +
					"      <p>Current Date: "+new Date() +"</p>\n" +
					"   \n" +
					"    </body>\n" +
					"</html>";

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));

			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();

			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes());
			worker.parseXHtml(writer, document, inputStream);
			document.close();
		} catch (DocumentException e) {
			// Handle DocumentException
		}
	}







}
