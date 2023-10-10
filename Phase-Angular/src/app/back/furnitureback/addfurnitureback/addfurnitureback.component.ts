import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Furniture } from 'src/app/modele/furniture';
import { FurnitureService } from 'src/app/shared/furniture.service';

@Component({
  selector: 'app-addfurnitureback',
  templateUrl: './addfurnitureback.component.html',
  styleUrls: ['./addfurnitureback.component.css']
})
export class AddfurniturebackComponent implements OnInit {
  furniturelist: any ;
  furnitureweight!: number;
  price!: number;
  surface!: number;
  picture!: string;
  furniture : Furniture = new Furniture();
  imageFile: File | null = null;
  form!: FormGroup;
  idOffre : any;

  constructor(private fb: FormBuilder,private furnitureservice: FurnitureService,private router : ActivatedRoute,private routerNav: Router, private route : Router) {
    this.form = this.fb.group({
      furnitureid: ['', Validators.required],
      furnitureweight: ['', Validators.required],
      price: ['', Validators.required],
      surface: ['', Validators.required],
      picture: ['', Validators.required]
    });
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
    this.route.navigate(['/listfurnitureback']);
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.furniture.picture = file.name;
  }

}
