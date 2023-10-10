import { Component, OnInit } from '@angular/core';
import { Delivery } from 'src/app/shared/Moduels/delivery/delivery.module';
import { DeliveryService } from 'src/app/shared/Service/delivery.service';

@Component({
  selector: 'app-adddelivery',
  templateUrl: './adddelivery.component.html',
  styleUrls: ['./adddelivery.component.css']
})
export class AdddeliveryComponent implements OnInit {
  delivery: Delivery=new Delivery(); 

  constructor(private deliveryservice : DeliveryService) { }

  ngOnInit(): void {
  }
  onSubmit() {
    
    this.deliveryservice.addDelivery(this.delivery, 1,1).subscribe();
}
}
