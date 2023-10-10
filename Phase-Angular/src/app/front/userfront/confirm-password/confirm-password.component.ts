import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { User } from 'src/app/modele/user/user';
import { AuthService } from 'src/app/shared/auth.service';

@Component({
  selector: 'app-confirm-password',
  templateUrl: './confirm-password.component.html',
  styleUrls: ['./confirm-password.component.css']
})
export class ConfirmPasswordComponent implements OnInit {


  user!:User;
  code!: number;
  confirmed: boolean = false;

  constructor(private cookieService: CookieService, private router: Router,private  authService: AuthService ) { }


  ngOnInit(): void {

  }

  @HostListener('window:beforeunload', ['$event'])
  beforeUnloadHandler(event: BeforeUnloadEvent) {
    if (!this.confirmed) {
      event.preventDefault();
      event.returnValue = false;
      this.cookieService.delete('token');
  this.router.navigateByUrl('/login');
  history.pushState(null,'', window.location.href);
  window.onpopstate = function () {
    history.go(1);
  };


    }
  }


  confirCode() {
    const token = this.cookieService.get('token');
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    let nbreRepetitions = 0;
    while (nbreRepetitions < 3) {
      const codeInput = (<HTMLInputElement>document.getElementById('subscribeEmail')).value;
      if (tokenPayload.code === codeInput ) {
        this.user.enabled = true;
        
        if (this.authService.roleMatch('ADMIN')) {
          this.router.navigateByUrl('/users');
        } else if (this.authService.roleMatch('CLIENT')) {
          this.router.navigateByUrl('/profile');
        }
        return true;
      }
      nbreRepetitions++;
    }
    return false;
  }


logout(): void {
  this.cookieService.delete('token');
  this.router.navigateByUrl('/login');
}

}



