import { Component, OnInit } from '@angular/core';
import { DeliveryService } from 'src/app/shared/Service/delivery.service';

@Component({
  selector: 'app-listdeliveryfront',
  templateUrl: './listdeliveryfront.component.html',
  styleUrls: ['./listdeliveryfront.component.css']
})
export class ListdeliveryfrontComponent implements OnInit {

  constructor(private deliveryservice : DeliveryService) { 
    
  }
  listdelivery: any;

  ngOnInit(): void {
    this.getAllRelocation();
  }
  getAllRelocation(){
    this.deliveryservice.getAllDelivery().subscribe(res => this.listdelivery = res)
  }

}
