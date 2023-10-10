import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/modele/user/user';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/shared/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {
  users!: User[];
  selectedUser!:User;
  list:any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];
  searchTerm: string = '';
  title = 'Angular Search Using ng2-search-filter';
  searchText!:any;
  constructor(private userService:UserService, private router:Router) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(
      res=>{
       this.users=res;

      }

          )
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.ngOnInit();
  }

  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ngOnInit();
  }
  onDeletUser = (id: number) => {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce poste?')) {
      this.userService.deleteUser(id).subscribe(() => {
        // Recharge la page après la suppression
        window.location.reload();
      });
    }
  }

  onUpdateUser(user: any ) {
    this.selectedUser = user;
    this.router.navigate(['/updateuser',user.userid])
  }


}
