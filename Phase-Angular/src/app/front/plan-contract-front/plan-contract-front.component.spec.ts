import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanContractFrontComponent } from './plan-contract-front.component';

describe('PlanContractFrontComponent', () => {
  let component: PlanContractFrontComponent;
  let fixture: ComponentFixture<PlanContractFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlanContractFrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanContractFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
