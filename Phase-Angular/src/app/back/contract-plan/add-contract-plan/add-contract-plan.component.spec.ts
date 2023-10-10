import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddContractPlanComponent } from './add-contract-plan.component';

describe('AddContractPlanComponent', () => {
  let component: AddContractPlanComponent;
  let fixture: ComponentFixture<AddContractPlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddContractPlanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddContractPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
