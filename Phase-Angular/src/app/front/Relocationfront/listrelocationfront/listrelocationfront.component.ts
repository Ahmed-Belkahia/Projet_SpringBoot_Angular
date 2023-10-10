import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelocationService } from 'src/app/shared/relocation.service';

@Component({
  selector: 'app-listrelocationfront',
  templateUrl: './listrelocationfront.component.html',
  styleUrls: ['./listrelocationfront.component.css']
})
export class ListrelocationfrontComponent implements OnInit {
  listrelocation: any;
  relocation : any;


  constructor(private relocationservice : RelocationService, private router:Router) { }

  ngOnInit(): void {
    this.getAllRelocation();
  }
  getAllRelocation() {
    this.relocationservice.getAllRelocation().subscribe((res: any) => {
      this.listrelocation = res.filter((relocation: { userRelocation: number; }) => relocation.userRelocation === 1);
    });
  }
  
  
  openMap(locationdep: string, locationarr: string) {
    var directionsUrl = "https://www.google.com/maps/dir/?api=1&origin=" + locationdep + "&destination=" + locationarr + "&travelmode=driving";
    window.open(directionsUrl, "_blank");
  }

}
