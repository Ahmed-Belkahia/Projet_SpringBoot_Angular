import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { roletype } from 'src/app/modele/role/roletype';
import { User } from 'src/app/modele/user/user';
import { AuthService } from 'src/app/shared/auth.service';
import { Location } from '@angular/common';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


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
    roles: roletype.CLIENT
  };

  constructor(private authService: AuthService, private cookieService: CookieService, private router:Router/*, private location: Location*/) { }

  ngOnInit(): void {
  }
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.user.profilepicture = reader.result as string;
    }
    reader.readAsDataURL(file);
  }

  Register() {

    this.authService.register(this.user).subscribe(
      user => {
        console.log('Utilisateur ajouté avec succès:', user);

        // Réinitialiser le formulaire
        this.cookieService.set('token', user.token, 1, '/', 'localhost', true, 'Lax');

        this.user = {
          userid: 0,
          cin: 0,
          firstname: '',
          lastname: '',
          email: '',
          password: '',
          phone: 0,
          address: '',
          profilepicture: '',
          enabled:false,
          companyname: '',
          roles:roletype.CLIENT
        };
        this.router.navigateByUrl('/conf');

      },
      error => {
        console.error('Erreur lors de l\'ajout de l\'utilisateur:', error);
      }
    );
  }

  onLogin(){
    this.router.navigateByUrl("/login");
  }



  // Ajouter une propriété pour stocker le rôle sélectionné


// Ajouter une méthode pour gérer le changement de sélection dans la liste déroulante


}
