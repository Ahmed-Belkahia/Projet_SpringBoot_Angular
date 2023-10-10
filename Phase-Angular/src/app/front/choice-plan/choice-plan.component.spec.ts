import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChoicePlanComponent } from './choice-plan.component';

describe('ChoicePlanComponent', () => {
  let component: ChoicePlanComponent;
  let fixture: ComponentFixture<ChoicePlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChoicePlanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChoicePlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
