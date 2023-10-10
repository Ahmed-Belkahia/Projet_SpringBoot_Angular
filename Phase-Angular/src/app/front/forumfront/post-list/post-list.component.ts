import { Component, OnInit } from '@angular/core';

import { Post } from 'src/app/modele/forum/post';
import { ServicePostService } from 'src/app/shared/service-post.service';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
posts: Post[]= [];
currentCategoryId: number=1;  
searchText!:any;
  constructor(private servicepost: ServicePostService,
    private route: ActivatedRoute,
    private cookieService: CookieService) { }
 list!:any;

 POSTS: any;
 page: number = 1;
 count: number = 0;
 tableSize: number = 7;
 tableSizes: any = [3, 6, 9, 12];
  ngOnInit() {
    this.listPosts();
  }

  listPosts() {
    
    this.servicepost.getPostList().subscribe(
      data => {
        this.posts = data;
        console.log(data);
      }
    )
    
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }

  public getFirstnameFromCookie(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.firstname as string;
      return userName
    }
    return "";
  }
  public getLastnameFromCookie(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.lastname as string;
      return userName
    }
    return "";
  }

}
