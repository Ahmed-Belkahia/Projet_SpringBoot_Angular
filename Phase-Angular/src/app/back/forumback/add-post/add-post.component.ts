import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/modele/forum/post';
import { ServicePostService } from 'src/app/shared/service-post.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  
  constructor(private service : ServicePostService, private router : Router) { }

  ngOnInit(): void {
  }
  Post: Post = new Post();
  id!: number;
  addPlanOffer() {
    if (!this.Post.title) {
      alert('Veuillez saisir un titre');
      return;
    }
    if (!this.Post.postContent) {
      alert('Veuillez saisir une description');
      return;
    }
    if (!this.Post.image) {
      alert('Veuillez saisir une image');
      return;
    }
    this.service.addPost(this.Post).subscribe();
    this.Post = new Post();
    console.log(Post);
    this.gotoList();
  }
  
gotoList() {
  
this.router.navigate(['/listpost']);

}
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.Post.image = file.name;
  }
}