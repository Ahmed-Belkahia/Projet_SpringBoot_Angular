import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/shared/auth.service';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  email!: string;
  password!: string;
  error!: string;

  constructor(private authService: AuthService, private router: Router, private cookieService: CookieService) {}

  onSubmit() {
    this.authService.login(this.email, this.password).subscribe(
      (result: any) => {
        if (result && result.token) {
          this.cookieService.set('token', result.token, 1, '/', 'localhost', true, 'Lax');
        }
        if (this.authService.roleMatch('ADMIN')) {
          this.router.navigateByUrl('/users');
        } else if (this.authService.roleMatch('CLIENT')) {
          this.router.navigateByUrl('/profile');
        }
      },
      (err) => {
        console.log('error occured!');
      }
    );
  }

  onTypeEmail(){
    this.router.navigateByUrl('/typeEmail');
  }

  ngOnInit(): void {}
}
