import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Delivery } from 'src/app/modele/delivery/delivery.module';
import { DeliveryService } from 'src/app/shared/delivery.service';

@Component({
  selector: 'app-adddeliveryfront',
  templateUrl: './adddeliveryfront.component.html',
  styleUrls: ['./adddeliveryfront.component.css']
})
export class AdddeliveryfrontComponent implements OnInit {
  delivery: Delivery=new Delivery(); 
  idOffre :any;
  constructor(private deliveryservice : DeliveryService, private router : ActivatedRoute,private route:Router) { 
    this.idOffre = this.router.snapshot.params['id']
  }

  ngOnInit(): void {
  }
  onSubmit() {
    
    this.deliveryservice.addDelivery(this.delivery, this.idOffre).subscribe(

    );
    this.gotoList();

}
gotoList() {
  this.route.navigate(['/listfurniturefront']);
}

}
