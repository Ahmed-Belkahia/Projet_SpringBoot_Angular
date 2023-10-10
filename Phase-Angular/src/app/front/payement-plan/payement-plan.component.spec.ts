import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayementPlanComponent } from './payement-plan.component';

describe('PayementPlanComponent', () => {
  let component: PayementPlanComponent;
  let fixture: ComponentFixture<PayementPlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PayementPlanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PayementPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
