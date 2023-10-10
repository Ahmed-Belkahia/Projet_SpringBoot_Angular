import { ResourceLoader } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payment } from 'src/app/modele/Payment/payment.modele';
import { PaymentService } from 'src/app/shared/payment.service';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';

@Component({
  selector: 'app-offerpayment',
  templateUrl: './offerpayment.component.html',
  styleUrls: ['./offerpayment.component.css']
})
export class OfferpaymentComponent implements OnInit {

  constructor(private chargeService : PaymentService,private service2 : ServiceOffreService,private router : ActivatedRoute,private routerNav: Router, private route : Router) { 
    this.id = this.router.snapshot.params['id']

  }
  payement : Payment = new Payment();
  token!: string;
  currency!: string;
  amount!:number;
  id!: number;
  list!:any
  ngOnInit(): void {
  }
  onSubmit() {
    this.chargeService.createCharge1(this.token, this.currency, this.id).subscribe(
      response => { 
        console.log(response);
      },
      error => {
        console.log(error);
      }
     
      
    );
    
    this.route.navigate(['/listeofferfront']);
    alert('Payment validé');
    
  }
  
}