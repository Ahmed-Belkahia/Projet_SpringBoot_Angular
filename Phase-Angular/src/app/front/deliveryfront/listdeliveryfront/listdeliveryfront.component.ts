import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DeliveryService } from 'src/app/shared/delivery.service';

@Component({
  selector: 'app-listdeliveryfront',
  templateUrl: './listdeliveryfront.component.html',
  styleUrls: ['./listdeliveryfront.component.css']
})
export class ListdeliveryfrontComponent implements OnInit {
  constructor(private deliveryservice : DeliveryService,private router : Router) { 
    
  }
  listdelivery: any;

  ngOnInit(): void {
    this.getAllRelocation();
  }
  getAllRelocation(){
    this.deliveryservice.getAllDelivery().subscribe(res => this.listdelivery = res)
  }
  addpayement(i: any) {
    console.log('update', i)
    this.router.navigate(['/addpayment',i])
  }
}
