import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Furniture } from 'src/app/modele/furniture';
import { FurnitureService } from 'src/app/shared/furniture.service';

@Component({
  selector: 'app-updatefurnitureback',
  templateUrl: './updatefurnitureback.component.html',
  styleUrls: ['./updatefurnitureback.component.css']
})
export class UpdatefurniturebackComponent implements OnInit {

  id: number;
  obj: any = {}
  furniture : Furniture = new Furniture()
  imageFile: File | null = null;

  constructor(private service: FurnitureService, private route: ActivatedRoute, private routerNav: Router) { 
    this.id = this.route.snapshot.params['deliveryid'];
    this.service.getRelocationById(this.id).subscribe(res => {
      console.log(res)
      this.obj = res
      this.furniture = this.obj
      console.log('-----------', this.furniture)

    })

  }

  ngOnInit(): void {
  }

  onSubmit() {

    console.log(this.furniture)
    this.service.EditFurniture(this.furniture.furnitureid,this.furniture).subscribe(res => {
      console.log(res)
      this.routerNav.navigate(['/listfurnitureback'])
    }, err => {
      this.routerNav.navigate(['/listfurnitureback'])

    })
  }
  onImageSelected(event: any) {
    const files = event.target.files;
    if (files.length > 0) {
      this.imageFile = files[0];
    }
  }

}
