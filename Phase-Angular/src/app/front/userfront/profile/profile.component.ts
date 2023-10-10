import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { User } from 'src/app/modele/user/user';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User = {
    userid: 0,
    cin: 0,
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    phone: 0,
    address: '',
    profilepicture: '',
    companyname: '',
    enabled:false,
    roles: 0
  };
  constructor(private cookieService:CookieService, private userService:UserService, private router:Router) { }

  ngOnInit(): void {}


  onUpdateUser() {
    this.userService.updateUser(this.user).subscribe(res => {
      this.router.navigate(['/profile'])
    }, err => {
      this.router.navigate(['/profile'])

    })
  }

  public getEmail(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.sub as string;
      return userName
    }
    return "";
  }
  public getPhone(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.phone as string;
      return userName
    }
    return "";
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
  public getAddress(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var address = payLoad.address as string;
      return address
    }
    return "";
  }


  public getRoleFromCookies(): string {

      var token = this.cookieService.get("token");
      if (token != null && token.length != 0) {
        var payLoad = JSON.parse(window.atob(token.split('.')[1]));
        var userName = payLoad.roles as string;
        return userName
      }
      return "";
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
