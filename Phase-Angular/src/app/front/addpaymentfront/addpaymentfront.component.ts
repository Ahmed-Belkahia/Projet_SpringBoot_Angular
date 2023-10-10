import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payment } from 'src/app/modele/Payment/payment.modele';
import { PaymentService } from 'src/app/shared/payment.service';

@Component({
  selector: 'app-addpaymentfront',
  templateUrl: './addpaymentfront.component.html',
  styleUrls: ['./addpaymentfront.component.css']
})
export class AddpaymentfrontComponent implements OnInit {
  

  constructor(private chargeService : PaymentService,private router : ActivatedRoute,private routerNav: Router, private route : Router) { 
    this.iddelivery = this.router.snapshot.params['id']

  }
  payement : Payment = new Payment();
  token!: string;
  currency!: string;
  iddelivery!: number;
  ngOnInit(): void {
  }
  onSubmit() {
    this.chargeService.createCharge(this.token, this.currency, this.iddelivery).subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error);
      }
    );
  }
  onClickTelechargerFacturePDF(id: number) {
    this.chargeService.telechargerFacturePDF(id).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = URL.createObjectURL(blob);
      window.open(url);
    });
  }
}
