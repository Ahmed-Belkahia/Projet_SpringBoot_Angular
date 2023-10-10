import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Saleoffer } from 'src/app/modele/sale/saleoffer.modele';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';
@Component({
  selector: 'app-listefavoritefront',
  templateUrl: './listefavoritefront.component.html',
  styleUrls: ['./listefavoritefront.component.css']
})
export class ListefavoritefrontComponent implements OnInit {
  offers!: Observable<Saleoffer[]>;
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;

  constructor(private service :ServiceOffreService, private router:Router) { }

  ngOnInit(): void {
    this.service.getallfavorit().subscribe(
      (res) => {
        this.list = res;
        console.log(res);
      }

    )
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
      this.service.deleteOfferfavorit(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
      window.location.reload();
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
    console.log(`Offer ${id} favorie!`);
    this.router.navigate(['/listeofferfavorite']);
  }
  getallfavorit(){
    this.service.getallfavorit().subscribe((res)=>{
      this.service=res
      console.log(res);
    })
  }
 

 

  



}
