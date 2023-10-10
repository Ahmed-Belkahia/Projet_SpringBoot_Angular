import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Delivery } from 'src/app/modele/delivery/delivery.module';
import { DeliveryService } from 'src/app/shared/delivery.service';

@Component({
  selector: 'app-updatedeliveryback',
  templateUrl: './updatedeliveryback.component.html',
  styleUrls: ['./updatedeliveryback.component.css']
})
export class UpdatedeliverybackComponent implements OnInit {

  id: number;
  obj: any = {}
  delivery: Delivery = new Delivery()

  constructor(private service: DeliveryService, private route: ActivatedRoute, private routerNav: Router
  ) {
    this.id = this.route.snapshot.params['deliveryid'];
    this.service.getDeliveryById(this.id).subscribe(res => {
      console.log(res)
      this.obj = res
      this.delivery = this.obj
      console.log('-----------', this.delivery)

    })
  }
  ngOnInit(): void {
  }
  onSubmit() {

    console.log(this.delivery)
    this.service.EditDelivery(this.delivery.deliveryid,this.delivery).subscribe(res => {
      console.log(res)
      this.routerNav.navigate(['/listdeliveryback'])
    }, err => {
      this.routerNav.navigate(['/listdeliveryback'])

    })
  }
}
