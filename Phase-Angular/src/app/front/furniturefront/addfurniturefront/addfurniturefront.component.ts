import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Furniture } from 'src/app/modele/furniture';
import { FurnitureService } from 'src/app/shared/furniture.service';

@Component({
  selector: 'app-addfurniturefront',
  templateUrl: './addfurniturefront.component.html',
  styleUrls: ['./addfurniturefront.component.css']
})
export class AddfurniturefrontComponent implements OnInit {

  furniturelist: any ;
  furnitureweight!: number;
  price!: number;
  surface!: number;
  picture!: string;
  furniture : Furniture = new Furniture();
  imageFile: File | null = null;
  
  idOffre : any;

  constructor(private furnitureservice: FurnitureService,private router : ActivatedRoute,private routerNav: Router, private route : Router) {
  
    this.idOffre = this.router.snapshot.params['id']

  }

  ngOnInit(): void {
  }

   
  save() {
    this.furnitureservice
      .addFurniture(this.furniture,this.idOffre)
      .subscribe({
        next: data => {
          console.log("***********"+data);
          this.furniture = new Furniture();
          this.gotoList();
        },
        error: error => console.log(error)
      });
  }
  gotoList() {
    this.route.navigate(['/listfurniturefrontclient']);
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.furniture.picture = file.name;
  }

}
