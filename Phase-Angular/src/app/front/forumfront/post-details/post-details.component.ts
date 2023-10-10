import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/modele/forum/post';
import { ServicePostService } from 'src/app/shared/service-post.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {

  post: any;
  searchText!:any;
  posts: Post[]= [];
  prost: Post = new Post();
  constructor(private servicepost: ServicePostService,
    private route: ActivatedRoute) { }

 ngOnInit(): void {
  this.route.paramMap.subscribe(() => {
    this.handlePostDetails();
  })
}
handlePostDetails() {

  // get the "id" param string. convert string to a number using the "+" symbol
  const theProductId: number = +this.route.snapshot.paramMap.get('id')!;

  this.servicepost.getOffreById(theProductId).subscribe(
    data => {
      this.post = data;
    }
  )
}
listPosts() {
    
  this.servicepost.getPostList().subscribe(
    data => {
      this.posts = data;
      console.log(data);
    }
  )
  
}

}

