import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddtaxpaymentComponent } from './addtaxpayment.component';

describe('AddtaxpaymentComponent', () => {
  let component: AddtaxpaymentComponent;
  let fixture: ComponentFixture<AddtaxpaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddtaxpaymentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddtaxpaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
