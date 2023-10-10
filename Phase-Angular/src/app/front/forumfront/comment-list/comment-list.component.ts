import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Comment } from 'src/app/modele/forum/comment.modele';
import { Observable } from 'rxjs';
import { ForumService } from 'src/app/shared/forum.service';
@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css']
})
export class CommentListComponent implements OnInit {

  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 3;
  tableSizes: any = [3, 6, 9, 12];

  offers!: Observable<Comment[]>;
  list:any;
  postId: number;
  comments: Comment[];

  constructor(private service: ForumService) { }

 
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
    alert('Vous devez etre en admin pour supprimer ce commentaire');
      return;
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
  filterBadWords(text: string): string {
    const badWords = ['bad', 'worse', 'terrible','Fuck','shit','bastard','Rubbish','damn'];
    let filteredText = text;
    for (const word of badWords) {
      const regex = new RegExp('\\b' + word + '\\b', 'gi');
      filteredText = filteredText.replace(regex, '*'.repeat(word.length));
    }
    return filteredText;
  }
}
