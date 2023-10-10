import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutRentalOfferComponent } from './ajout-rental-offer.component';

describe('AjoutRentalOfferComponent', () => {
  let component: AjoutRentalOfferComponent;
  let fixture: ComponentFixture<AjoutRentalOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjoutRentalOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutRentalOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
