import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/auth.service';


@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.css']
})
export class ChangepasswordComponent implements OnInit {

  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
  message: string;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  updatePassword() {
    this.authService.updatePassword(this.oldPassword, this.newPassword)
    .subscribe(
      response => {
        console.log('Password updated successfully!');
        // TODO: Show success message to the user
      },
      error => {
        console.error(error);
        // TODO: Show error message to the user
      }
    );
  }


}
