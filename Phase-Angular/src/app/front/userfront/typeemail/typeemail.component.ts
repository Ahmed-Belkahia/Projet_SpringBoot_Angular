import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/shared/auth.service';

@Component({
  selector: 'app-typeemail',
  templateUrl: './typeemail.component.html',
  styleUrls: ['./typeemail.component.css']
})
export class TypeemailComponent implements OnInit {
email!:string;

  constructor(private authService:AuthService, private cookieService:CookieService, private router:Router) { }

  ngOnInit(): void {
  }

  sendEmail(){
    this.authService.sendEmailForgot(this.email).subscribe(
      (result: any) => {
        if (result && result.token) {
          this.cookieService.set('token', result.token, 1, '/', 'localhost', true, 'Lax');
          this.router.navigateByUrl('/confirmRegister');
        }
      },
      (err) => {
        console.log('error occured!');
      }

    );
  }

}
