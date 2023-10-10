import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Rendezvous } from 'src/app/modele/sale/rendezvous.modele'; 
import { ServiceRendezvousService } from 'src/app/shared/service-rendezvous.service';


@Component({
  selector: 'app-addrendezvous',
  templateUrl: './addrendezvous.component.html',
  styleUrls: ['./addrendezvous.component.css']
})
export class AddrendezvousComponent implements OnInit {

  constructor(private service:ServiceRendezvousService, private route:ActivatedRoute, private router:Router) {
    this.idOffre = this.route.snapshot.params['id']
   }
   
   idOffre!:any;
   rendezvous: Rendezvous=new Rendezvous();
  ngOnInit(): void {
  }
  async Demanderrendezvous() {
    console.log('aaa', this.rendezvous);
    
      await this.service.demanderRendezVous(this.rendezvous, this.idOffre).subscribe();
      this.rendezvous = new Rendezvous();
    
      this.router.navigate(['/listeofferfront']);
      alert('le rendez_vous a ete ajouter avec succes')
     
    };
    
  
   
  

}
