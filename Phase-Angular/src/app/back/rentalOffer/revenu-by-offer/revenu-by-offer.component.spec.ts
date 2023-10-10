import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenuByOfferComponent } from './revenu-by-offer.component';

describe('RevenuByOfferComponent', () => {
  let component: RevenuByOfferComponent;
  let fixture: ComponentFixture<RevenuByOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevenuByOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RevenuByOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
