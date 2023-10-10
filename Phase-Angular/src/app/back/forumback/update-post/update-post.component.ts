import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/modele/forum/post';
import { ServicePostService } from 'src/app/shared/service-post.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  id!:any;
Post : Post = new Post();
obj!:any;
  constructor( private service : ServicePostService, private route: ActivatedRoute, private routerNav: Router
    ) { this.id = this.route.snapshot.params['id'];
    this.service.getOffreById(this.id).subscribe(res => {
      console.log(res)
      this.obj = res
      this.Post = this.obj
      console.log('-----------', this.Post)

    })
  }

  ngOnInit(): void {

  }



  updatepost() {
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
    console.log(this.Post)
    this.service.updatePost(this.Post).subscribe(res => {
      console.log(res)
      this.routerNav.navigate(['/listpost'])
    }, err => {
      this.routerNav.navigate(['/listpost'])

    })
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.Post.image = file.name;
  }
  
}
