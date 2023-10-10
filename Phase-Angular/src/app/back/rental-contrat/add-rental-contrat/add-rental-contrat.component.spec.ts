import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRentalContratComponent } from './add-rental-contrat.component';

describe('AddRentalContratComponent', () => {
  let component: AddRentalContratComponent;
  let fixture: ComponentFixture<AddRentalContratComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddRentalContratComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRentalContratComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
