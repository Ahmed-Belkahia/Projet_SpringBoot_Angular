import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Relocation } from 'src/app/modele/relocation.module';
import { RelocationService } from 'src/app/shared/relocation.service';

@Component({
  selector: 'app-addrelocationback',
  templateUrl: './addrelocationback.component.html',
  styleUrls: ['./addrelocationback.component.css']
})
export class AddrelocationbackComponent implements OnInit {

  listrelocation: any;
  relocation: Relocation=new Relocation();
  relocationid!: number;
  relocationdate!: Date;

  constructor(private relocationservice : RelocationService, private router:Router) { }

  ngOnInit(): void {
  }
  
  
  onSubmit() {
    this.relocationservice.addRelocatio(this.relocation, 1).subscribe((relocationId: number) => {
      const url = 'addfurnitureback/' + relocationId;
      this.router.navigateByUrl(url);
    });
    this.relocation = new Relocation();
  }
  
  
  

}
