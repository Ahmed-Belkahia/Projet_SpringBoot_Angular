import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeRentalFrontComponent } from './liste-rental-front.component';

describe('ListeRentalFrontComponent', () => {
  let component: ListeRentalFrontComponent;
  let fixture: ComponentFixture<ListeRentalFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeRentalFrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeRentalFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
