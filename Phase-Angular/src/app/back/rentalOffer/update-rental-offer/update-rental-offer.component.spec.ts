import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateRentalOfferComponent } from './update-rental-offer.component';

describe('UpdateRentalOfferComponent', () => {
  let component: UpdateRentalOfferComponent;
  let fixture: ComponentFixture<UpdateRentalOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateRentalOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateRentalOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
