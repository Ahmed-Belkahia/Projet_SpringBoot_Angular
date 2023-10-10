import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DeliveryService } from 'src/app/shared/delivery.service';

@Component({
  selector: 'app-listdeliveryback',
  templateUrl: './listdeliveryback.component.html',
  styleUrls: ['./listdeliveryback.component.css']
})
export class ListdeliverybackComponent implements OnInit {
  listdelivery: any;
  bestDelivery!: string;
  list:any;
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 3;
  tableSizes: any = [3, 6, 9, 12];

  constructor(private deliveryservice : DeliveryService,     private router : Router
    ) { }

  ngOnInit(): void {
    this.getAllRelocation();
  }
  getAllRelocation(){
    this.deliveryservice.getAllDelivery().subscribe(res => this.listdelivery = res)
  }
  update(i: any) {
    console.log('update', i)
    this.router.navigate(['/updatedeliveryback',i])
  }
  deleteDelivery = (deliveryid: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.deliveryservice.DelateDelivery(deliveryid).subscribe(() => {
        // Recharge la page après la suppression
        
      });
      this.getAllRelocation();

    }
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ngOnInit();
  }

  findBestDelivery() {
    this.deliveryservice.getBestDelivery().subscribe(
      data => {
        this.bestDelivery = data;
        console.log(this.bestDelivery);
      },
      error => console.error(error)
    );

}}
