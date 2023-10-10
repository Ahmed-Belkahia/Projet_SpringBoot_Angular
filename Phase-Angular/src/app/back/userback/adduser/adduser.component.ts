import { Component, OnInit } from '@angular/core';
import { roletype } from 'src/app/modele/role/roletype';
import { User } from 'src/app/modele/user/user';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.css']
})
export class AdduserComponent implements OnInit {
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
  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onAddUser() {
    this.userService.addUser(this.user).subscribe(
      user => {
        console.log('Utilisateur ajouté avec succès:', user);
        // Réinitialiser le formulaire
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
          companyname: '',
          enabled:false,
          roles: 0
        };
      },
      error => {
        console.error('Erreur lors de l\'ajout de l\'utilisateur:', error);
      }
    );
  }
  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.user.profilepicture = file.name;
  };
  // Ajouter une propriété pour stocker le rôle sélectionné


// Ajouter une méthode pour gérer le changement de sélection dans la liste déroulante



}
