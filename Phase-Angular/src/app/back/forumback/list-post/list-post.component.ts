import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServicePostService } from 'src/app/shared/service-post.service';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {
  list!:any;
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  constructor(private service : ServicePostService, private router : Router) { }

  ngOnInit(): void {
    this.service.getPostList().subscribe(
      (res)=>{
       this.list=res;
       console.log(res);
  })

}
deletepost = (id: number) => {
  if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
    this.service.deletePost(id).subscribe(() => {
      // Recharge la page après la suppression
      window.location.reload();
    });
  }
}
update(i: any) {
  console.log('update', i)
  this.router.navigate(['/updatepost',i.postId])
}
filterD(){
  this.list = this.list.sort(function(a:any, b:any){
    return b.postId - a.postId
  })
}

filterA(){
  this.list = this.list.sort(function(a:any, b:any){
    return a.postId - b.postId
  })
}
onTableDataChange(event: any) {
  this.page = event;
  this.ngOnInit();
}
}