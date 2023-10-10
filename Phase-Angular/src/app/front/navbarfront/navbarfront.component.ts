import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/shared/auth.service';

import { RentalContractServiceService } from 'src/app/shared/rental-contract-service.service';

@Component({
  selector: 'app-navbarfront',
  templateUrl: './navbarfront.component.html',
  styleUrls: ['./navbarfront.component.css']
})
export class NavbarfrontComponent implements OnInit {

  list!:any
number: number = 2;
boolean: boolean ;
contratYoufa : any=[];

  constructor(private service: RentalContractServiceService,private cookieService: CookieService, private router:Router, public authService:AuthService) { }
  token!: string;
  ngOnInit(): void {
    if (this.authService.checkToken()) {
      // Si un token est présent, extraire le nom d'utilisateur à partir du token
      const token = this.cookieService.get('token');
    }
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

    this.service.getUsersFinContrat().subscribe(
      (res) => {
        this.list = res;
        this.list.map((i:any)=>{
          if(i==2){
            this.boolean=true
          }
        })
        console.log("--------------"+res);
      }

    )
    //const boolean = this.list.some((value) => value === 2);
  this.service.getContratYoufaBaadTroisJoirs(2).subscribe(res =>
    {
      this.contratYoufa=JSON.parse(res) ;
      console.log("+++++++++"+res) ;

    } )

  }
  
/*
  list!:any

getIfUserHasEndContrat() : Boolean {
  return this.list.includes(authenticatedUserId);
}
*/

detail(id:number){
  console.log(id)
  const url = 'DetailsContrat/'+id
  this.router.navigateByUrl(url)
  
}

  public getProfilePictureFromCookie(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      const imageName = payLoad.profilepicture as string;
      return `assets/images/team/${imageName}`;
    }
    return "";
  }
  logout(): void {
    this.cookieService.delete('token');
    this.router.navigateByUrl('/login');
  }

  onLogin(): void{
    this.router.navigateByUrl('/login');
  }
  changepassword(){
    this.router.navigateByUrl('/changepassword')
  }


}
