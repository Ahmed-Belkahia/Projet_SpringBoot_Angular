import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddpaymentfrontComponent } from './addpaymentfront.component';

describe('AddpaymentfrontComponent', () => {
  let component: AddpaymentfrontComponent;
  let fixture: ComponentFixture<AddpaymentfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddpaymentfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddpaymentfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
