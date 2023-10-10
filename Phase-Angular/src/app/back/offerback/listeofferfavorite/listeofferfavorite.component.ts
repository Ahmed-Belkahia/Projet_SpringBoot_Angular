import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Saleoffer } from 'src/app/modele/sale/saleoffer.modele';
import { ServiceOffreService } from 'src/app/shared/service-offre.service';

@Component({
  selector: 'app-listeofferfavorite',
  templateUrl: './listeofferfavorite.component.html',
  styleUrls: ['./listeofferfavorite.component.css']
})
export class ListeofferfavoriteComponent implements OnInit {
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
    }
  }
  updateoffre(i: any) {
    console.log('update', i)
    this.router.navigate(['/updateoffreback',i.sellid])
  } 
  
  detail(data:any){
    console.log(data)
    const url = 'addcontract/'+data.sellid
    this.router.navigateByUrl(url)
  }
  markOfferAsFavorie(id: number) {
    this.service.markOfferAsFavorie(id);
    console.log(`Offer ${id} marked as interessant!`);
  }
  getallfavorit(){
    this.service.getallfavorit().subscribe((res)=>{
      this.service=res
      console.log(res);
    })
  }

}
