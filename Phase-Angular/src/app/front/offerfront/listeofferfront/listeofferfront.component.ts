import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Saleoffer } from 'src/app/modele/sale/saleoffer.modele';
import { StatutOffre } from 'src/app/modele/sale/statutoffre';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';

@Component({
  selector: 'app-listeofferfront',
  templateUrl: './listeofferfront.component.html',
  styleUrls: ['./listeofferfront.component.css']
})
export class ListeofferfrontComponent implements OnInit {
  offers!: Observable<Saleoffer[]>;
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  rating!: number;
  statutOffre =StatutOffre ;
  constructor(private service :ServiceOffreService, private router:Router) { }

  ngOnInit(): void {
    this.retrieveOffres();
    
  }
  retrieveOffres(): void {
    this.service.getAllOffer().subscribe(
      (res:any) => {
        this.list = res.filter(( saleoffer: {sold:boolean;}) => saleoffer.sold === false );
        console.log(res);
        
      }
      
    );
    
  }
 
  Selleroffer:Saleoffer=new Saleoffer();
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.Selleroffer.picture = reader.result as string; // mettre à jour l'attribut "picture" de votre objet "Selleroffer"
      };
    }
  }
  
  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }
  deleteOffer = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet offre')) {
      this.service.deleteOffer(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
  }
  updateoffre(txt: any) {
    console.log('update', txt)
    this.router.navigate(['/updateplan',txt.planid])
  }
  
  detail(data:any){
    console.log(data)
    const url = 'addcontractfront/'+data.sellid
        this.router.navigateByUrl(url)
  }
  demande(data:any){
    console.log(data)
    const url = 'addrendezvous/'+data.sellid
        this.router.navigateByUrl(url)
  }
  markOfferAsFavorie(id: number) {
    this.service.markOfferAsFavorie(id);
    console.log(`Offer ${id} marked as interessant!`);
   
    
  }
  isAddContractDisabled(offre: Saleoffer): boolean {
    return offre.statut === 'AVEC_RENDEZ_VOUS';
  }
  
 

 

  



}
