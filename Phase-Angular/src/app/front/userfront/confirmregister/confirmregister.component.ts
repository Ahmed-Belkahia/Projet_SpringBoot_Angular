import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-confirmregister',
  templateUrl: './confirmregister.component.html',
  styleUrls: ['./confirmregister.component.css']
})
export class ConfirmregisterComponent implements OnInit {

  code!: number;
  confirmed: boolean = false;

  constructor(private cookieService: CookieService, private router: Router) { }

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


  confirmCode() {
    const token = this.cookieService.get('token');
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    let nbreRepetitions = 0;
    while (nbreRepetitions < 3) {
      const codeInput = (<HTMLInputElement>document.getElementById('subscribeEmail')).value;
      if (tokenPayload.code === codeInput ) {
        this.confirmed = true;
        this.router.navigateByUrl('/forgotpwd');
        return true;
      }
      nbreRepetitions++;
    }
    this.logout();
    return false;
  }


logout(): void {
  this.cookieService.delete('token');
  this.router.navigateByUrl('/login');
}

}



