package com.example.techmasterpi;


import com.example.techmasterpi.service.AuthenticationService;

import com.example.techmasterpi.service.RentalContractService;
import com.example.techmasterpi.service.RentalOfferService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.Date;

@EnableScheduling

@SpringBootApplication
public class TechmasterpiApplication {
	public static void main(String[] args) {


		SpringApplication.run(TechmasterpiApplication.class, args);
		RentalOfferService rentalOfferService=new RentalOfferService();
		RentalContractService rentalContractService=new RentalContractService();
		//rentalOfferService.scrapping(100,750);

	}


}
