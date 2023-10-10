import { Component, OnInit } from '@angular/core';
import { ForumService } from 'src/app/shared/forum.service';
import { Observable } from 'rxjs';

import { Comment } from 'src/app/modele/forum/comment.modele';
@Component({
  selector: 'app-listecomment',
  templateUrl: './listecomment.component.html',
  styleUrls: ['./listecomment.component.css']
})
export class ListecommentComponent implements OnInit {


  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];

  offers!: Observable<Comment[]>;
  list:any;
  constructor(private service : ForumService) { }

  ngOnInit(): void {
    this.service.getOffers().subscribe(
      (res)=>{
       this.list=res;
       console.log(res);
      }
      
          )
  }
  reloadData() {
    this.list = this.service.getOffers();
  }

  deleteOffer = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.service.deleteOfferdeleteOffer(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ngOnInit();
  }

}
