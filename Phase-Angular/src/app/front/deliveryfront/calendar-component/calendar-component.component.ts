import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Calendar } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import { Delivery } from 'src/app/modele/delivery/delivery.module';
import { Relocation } from 'src/app/modele/relocation.module';
import { DeliveryService } from 'src/app/shared/delivery.service';
import { RelocationService } from 'src/app/shared/relocation.service';
@Component({
  selector: 'app-calendar-component',
  templateUrl: './calendar-component.component.html',
  styleUrls: ['./calendar-component.component.css']
})
export class CalendarComponentComponent implements OnInit {
  calendar!: Calendar;
  calendarOptions: any;
  ngOnInit(): void {
    this.getALLRDV();
    const calendarEl = document.getElementById('calendar');
    if (calendarEl) {
      this.calendar = new Calendar(calendarEl, {
        plugins: [dayGridPlugin, timeGridPlugin],
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        initialDate: new Date(),
        events: [],
        eventClick: (info) => {
          alert(info.event.extendedProps['description']);

          // rediriger vers la page de détails en utilisant le router
          this.router.navigate(['/addpayment', info.event.id]);
        }
      });
      this.calendar.render();
  }
}
getRelocationDate(deliveryId: string): Promise<Date> {
  return new Promise((resolve, reject) => {
    this.relocationService.getRelocationById1(deliveryId).subscribe((relocation: Relocation) => {
      const relocationDate = relocation.relocationdate;
      resolve(relocationDate);
    }, (error) => {
      reject(error);
    });
  });
}
constructor(private RDVService : DeliveryService,private relocationService : RelocationService,private router:Router) {
}
relocation : Delivery[];
relocationDate: Date ;






async getALLRDV() {
  try {
    const rdvs = await this.RDVService.getAllDelivery().toPromise();
    if (Array.isArray(rdvs)) {
      const events = await Promise.all(rdvs.map(async (rdv) => {
        const relocationDate = await this.getRelocationDate(rdv.relocationDelivery);
        let eventColor = '';
switch (rdv.stateDeleviry) {
case 'Waitting for pay':
eventColor = 'red';
break;
case 'on the way':
eventColor = 'green';
break;
case 'Arrived':
eventColor = 'yellow';
break;
default:
eventColor = '';
break;
}
        return {
          id: rdv.deliveryid, // ajouter un identifiant unique

          title: rdv.state,
          start: relocationDate,
          end: relocationDate,
          backgroundColor: eventColor,
          extendedProps: {
            description: `Description de l'événement :
            track : ${rdv.track}
            service detail : ${rdv.servicedetail}`   // Ajoutez une description personnalisée
          }

        };
      }));
      this.calendar.addEventSource(events);
    }
  } catch (error) {
    console.error(error);
  }
}



  
    }
    
  






