import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Relocation } from 'src/app/modele/relocation.module';
import { RelocationService } from 'src/app/shared/relocation.service';

@Component({
  selector: 'app-listrelocationback',
  templateUrl: './listrelocationback.component.html',
  styleUrls: ['./listrelocationback.component.css']
})
export class ListrelocationbackComponent implements OnInit {
  listrelocation: any;
  relocation: Relocation=new Relocation();
  relocationid!: number;
  relocationdate!: Date;

  constructor(private relocationservice : RelocationService, private router:Router) { }

  ngOnInit(): void {
    this.getAllRelocation();
  }
  getAllRelocation(){
    this.relocationservice.getAllRelocation().subscribe(res => this.listrelocation = res)
  }
  
  onSubmit() {
    this.relocationservice.addRelocatio(this.relocation,1).subscribe();
    this.relocation = new Relocation(); 
   
  }
  update(i: any) {
    console.log('update', i)
    this.router.navigate(['/updaterelocationback',i])
  }
  detail(data:any){
    console.log(data)
    const url = 'adddeliveryback/'+data.relocationid
    this.router.navigateByUrl(url)
  }

  deleteProduct = (idrelo: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.relocationservice.deleteRelocation(idrelo).subscribe(() => this.getAllRelocation());
    }

    

}
openMap(locationdep: string, locationarr: string) {
  var directionsUrl = "https://www.google.com/maps/dir/?api=1&origin=" + locationdep + "&destination=" + locationarr + "&travelmode=driving";
  window.open(directionsUrl, "_blank");
}

}
