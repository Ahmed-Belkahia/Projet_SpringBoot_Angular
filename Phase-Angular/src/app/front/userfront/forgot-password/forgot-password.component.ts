import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
password!:string;
confirmPassword!:string;

constructor(private router:Router, private authService:AuthService) { }

  ngOnInit(): void {
  }

  onLogin(){
this.router.navigateByUrl("/login")
  }

  onForget(){
    if (this.password !== this.confirmPassword) {
      console.log('Les mots de passe ne correspondent pas');
    }
    this.authService.forgotPassword(this.password).subscribe();
  }

}
