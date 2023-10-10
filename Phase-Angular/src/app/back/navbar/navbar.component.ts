import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private cookieService:CookieService, private router: Router) { }
  searchText!:any;


  ngOnInit(): void {
  }

  logout(): void {
    this.cookieService.delete('token');
    this.router.navigateByUrl('/login');
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
  public getProfilePicture(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.profilepicture as string;
      return userName
    }
    return "";
  }



}
