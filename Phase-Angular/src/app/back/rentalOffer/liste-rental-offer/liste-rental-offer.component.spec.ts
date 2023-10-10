import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeRentalOfferComponent } from './liste-rental-offer.component';

describe('ListeRentalOfferComponent', () => {
  let component: ListeRentalOfferComponent;
  let fixture: ComponentFixture<ListeRentalOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeRentalOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeRentalOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
