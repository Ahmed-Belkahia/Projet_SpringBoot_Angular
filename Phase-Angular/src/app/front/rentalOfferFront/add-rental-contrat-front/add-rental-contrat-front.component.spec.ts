import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRentalContratFrontComponent } from './add-rental-contrat-front.component';

describe('AddRentalContratFrontComponent', () => {
  let component: AddRentalContratFrontComponent;
  let fixture: ComponentFixture<AddRentalContratFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddRentalContratFrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRentalContratFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
