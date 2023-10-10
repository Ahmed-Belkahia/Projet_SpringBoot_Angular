import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalContratComponent } from './rental-contrat.component';

describe('RentalContratComponent', () => {
  let component: RentalContratComponent;
  let fixture: ComponentFixture<RentalContratComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RentalContratComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalContratComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
