import { Component, OnInit } from '@angular/core';
import { ForumService } from 'src/app/shared/forum.service';
import { Comment } from 'src/app/modele/forum/comment.modele';
@Component({
  selector: 'app-addcomment',
  templateUrl: './addcomment.component.html',
  styleUrls: ['./addcomment.component.css']
})
export class AddcommentComponent implements OnInit {

  constructor( private services :ForumService) { }

  ngOnInit(): void {
  }
  Comment: Comment = new Comment();
  
  addPlanOffer() {
    if (!this.Comment.commentContent) {
      alert('Veuillez saisir une image');
      return;
    }
    this.services.addPlan(this.Comment).subscribe();
    this.Comment = new Comment();
    console.log(Comment);

    
  }
  

  }