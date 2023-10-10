import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/modele/user/user';
import { AuthService } from 'src/app/shared/auth.service';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-updateuser',
  templateUrl: './updateuser.component.html',
  styleUrls: ['./updateuser.component.css']
})
export class UpdateuserComponent implements OnInit {
  selectedUser: any;
  userid: any;
  obj: any = {};
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

  constructor(private router:Router, private userService: UserService, private route: ActivatedRoute) {
    this.userid = this.route.snapshot.params['userid'];
    this.userService.getUserById(this.userid).subscribe(res => {
      this.selectedUser = res
      this.user = this.selectedUser
    })
   }

  ngOnInit(): void {
  }

  onUpdateUser() {
    this.userService.updateUser(this.user).subscribe(res => {
      this.router.navigate(['/users'])
    }, err => {
      this.router.navigate(['/users'])

    })
  }

}
