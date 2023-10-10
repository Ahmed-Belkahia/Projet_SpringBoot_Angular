import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferpaymentComponent } from './offerpayment.component';

describe('OfferpaymentComponent', () => {
  let component: OfferpaymentComponent;
  let fixture: ComponentFixture<OfferpaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferpaymentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferpaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
