import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Relocation } from 'src/app/modele/relocation.module';
import { RelocationService } from 'src/app/shared/relocation.service';

@Component({
  selector: 'app-addrelocationfront',
  templateUrl: './addrelocationfront.component.html',
  styleUrls: ['./addrelocationfront.component.css']
})
export class AddrelocationfrontComponent implements OnInit {

  listrelocation: any;
  relocation: Relocation=new Relocation();
  relocationid!: number;
  relocationdate!: Date;

  constructor(private relocationservice : RelocationService, private router:Router) { }

  ngOnInit(): void {
  }
  
  
  onSubmit() {
    this.relocationservice.addRelocatio(this.relocation, 4).subscribe((relocationId: number) => {
      const url = 'addfurniturefront/' + relocationId;
      this.router.navigateByUrl(url);
    });
    this.relocation = new Relocation();
  }
  
  

}
