import { Component, OnInit } from '@angular/core';
import { ForumService } from 'src/app/shared/forum.service';
import { Comment } from 'src/app/modele/forum/comment.modele';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-commentadd',
  templateUrl: './commentadd.component.html',
  styleUrls: ['./commentadd.component.css']
})
export class CommentaddComponent implements OnInit {

  constructor( private services :ForumService) { }

  ngOnInit(): void {
  }
  Comment: Comment = new Comment();
  
  addPlanOffer() {
    
   
    if (!this.Comment.commentContent) {
      this.alertError();
      return;
    }
    this.services.addPlan(this.Comment).subscribe();
    this.Comment = new Comment();
    console.log(Comment);
    location.reload();
  }
  alertError() {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'please enter a comment ',
    });
}
}
