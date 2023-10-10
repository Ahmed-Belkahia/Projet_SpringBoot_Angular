import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payment } from 'src/app/modele/Payment/payment.modele';
import { AuthService } from 'src/app/shared/auth.service';
import { PaymentService } from 'src/app/shared/payment.service';

@Component({
  selector: 'app-addtaxpayment',
  templateUrl: './addtaxpayment.component.html',
  styleUrls: ['./addtaxpayment.component.css']
})
export class AddtaxpaymentComponent implements OnInit {
  constructor(private chargeService : PaymentService,private router : ActivatedRoute,private routerNav: Router, private authservice : AuthService) { 

  }
  payement : Payment = new Payment();
  token!: string;
  currency!: string;
  iddelivery!: number;
  ngOnInit(): void {
  }
  onSubmit() {
    this.chargeService.createChargetax(this.token, this.currency,this.authservice.getUserIdFromToken1()).subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error);
      }
    );
  }

}
