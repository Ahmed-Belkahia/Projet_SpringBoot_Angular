package com.example.techmasterpi.Controller;

import java.util.List;

import com.example.techmasterpi.domain.Reclamation;
import com.example.techmasterpi.service.ServiceReclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import com.example.techmasterpi.domain.ContractPlan;
import com.example.techmasterpi.model.ContractPlanDTO;

import com.example.techmasterpi.service.IContratPlan;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Reclamation")

public class ReclamationController {
	
	@Autowired
	ServiceReclamation reclamationService;




	@PostMapping("/AddReclamation/{idUser}")

	public void addReclamtion(@RequestBody Reclamation r)
	{
		reclamationService.addReclamtion(r);
	}


	@GetMapping("/export/{id}")
	public ResponseEntity<Resource> exportcontrat(@PathVariable int id) throws IOException {

		String filename = "Reclamation_" + id + ".pdf";
		String filePath = "C:/Users/AhmedBenAbdallah/Desktop/" + filename; // Update with your actual desktop path
		// Export the contract to PDF
		reclamationService.exportcontrat(id, filePath);

		// Prepare the file as a Resource
		File file = new File(filePath);
		Path path = file.toPath();
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		// Set the response headers
		HttpHeaders headers = new HttpHeaders();

		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

		// Return the file as a ResponseEntity
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/pdf"))
				.body(resource);

	}

}
