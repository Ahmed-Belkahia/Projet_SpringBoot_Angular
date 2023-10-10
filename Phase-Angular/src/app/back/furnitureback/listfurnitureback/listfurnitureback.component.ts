import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Furniture } from 'src/app/modele/furniture';
import { FurnitureService } from 'src/app/shared/furniture.service';

@Component({
  selector: 'app-listfurnitureback',
  templateUrl: './listfurnitureback.component.html',
  styleUrls: ['./listfurnitureback.component.css']
})
export class ListfurniturebackComponent implements OnInit {

  furniturelist: any ;
  furnitureweight!: number;
  price!: number;
  surface!: number;
  picture!: string;
  furniture : Furniture = new Furniture();
  imageFile: File | null = null;
  form!: FormGroup;
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 5;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;


  constructor(private fb: FormBuilder,private furnitureservice: FurnitureService,     private router : Router) {
    this.form = this.fb.group({
      furnitureid: ['', Validators.required],
      furnitureweight: ['', Validators.required],
      price: ['', Validators.required],
      surface: ['', Validators.required],
      picture: ['', Validators.required]
    });
  }
  update(i: any) {
    console.log('update', i)
    this.router.navigate(['/updatefurnitureback',i])
  }
  ngOnInit(): void {
    this.getAllfurniture();
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }
  deleteFurniter = (furnitureid: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.furnitureservice.deleteFurniture(furnitureid).subscribe(() => {
        // Recharge la page après la suppression
        
      });
      this.getAllfurniture();

    }
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ngOnInit();
  }

  getAllfurniture() {
    this.furnitureservice.getAllFurniture().subscribe(res => this.furniturelist = res)

  }
  onImageSelected(event: any) {
    const files = event.target.files;
    if (files.length > 0) {
      this.imageFile = files[0];
    }
  }
  
  
  

  
  
}