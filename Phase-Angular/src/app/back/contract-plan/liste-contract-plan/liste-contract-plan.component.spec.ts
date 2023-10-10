import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeContractPlanComponent } from './liste-contract-plan.component';

describe('ListeContractPlanComponent', () => {
  let component: ListeContractPlanComponent;
  let fixture: ComponentFixture<ListeContractPlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeContractPlanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeContractPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
