import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Relocation } from 'src/app/modele/relocation.module';
import { RelocationService } from 'src/app/shared/relocation.service';

@Component({
  selector: 'app-updaterelocationback',
  templateUrl: './updaterelocationback.component.html',
  styleUrls: ['./updaterelocationback.component.css']
})
export class UpdaterelocationbackComponent implements OnInit {
  id: number;
  obj: any = {}
  relocation : Relocation = new Relocation()
  constructor(private service: RelocationService, private route: ActivatedRoute, private routerNav: Router) { 
    this.id = this.route.snapshot.params['deliveryid'];
    this.service.getRelocationById(this.id).subscribe(res => {
      console.log(res)
      this.obj = res
      this.relocation = this.obj
      console.log('-----------', this.relocation)

    })

  }

  ngOnInit(): void {
  }

  onSubmit() {

    console.log(this.relocation)
    this.service.EditRelocation(this.relocation.relocationid,this.relocation).subscribe(res => {
      console.log(res)
      this.routerNav.navigate(['/listrelocationback'])
    }, err => {
      this.routerNav.navigate(['/listrelocationback'])

    })
  }

}
