import { Component, OnInit } from '@angular/core';
import { Saleoffer } from 'src/app/modele/sale/saleoffer.modele';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TypeOffer } from 'src/app/modele/sale/typeoffer';

@Component({
  selector: 'app-updateoffreback',
  templateUrl: './updateoffreback.component.html',
  styleUrls: ['./updateoffreback.component.css']
})
export class UpdateoffrebackComponent implements OnInit {
  id: any
  obj: any = {}
  saleoffer: Saleoffer = new Saleoffer();
  typesOffre = Object.values(TypeOffer);

  constructor(private service: ServiceOffreService,private route:ActivatedRoute,private router:Router) { 
    this.id = this.route.snapshot.params['id'];
    this.service.getOffreById(this.id).subscribe(res => {
      console.log(res)
      this.obj = res
      this.saleoffer = this.obj
      console.log('-----------', this.saleoffer)})}
  

  ngOnInit(): void {
  }
  updateoffre() {

    console.log(this.saleoffer)
    this.service.updateoffre(this.saleoffer).subscribe(res => {
      console.log(res)
      this.router.navigate(['/listeoffer'])
    }, err => {
      this.router.navigate(['/listeoffer'])

    })
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.saleoffer.picture = file.name;
  }
}
