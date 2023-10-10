import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractPlanComponent } from './contract-plan.component';

describe('ContractPlanComponent', () => {
  let component: ContractPlanComponent;
  let fixture: ComponentFixture<ContractPlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractPlanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
