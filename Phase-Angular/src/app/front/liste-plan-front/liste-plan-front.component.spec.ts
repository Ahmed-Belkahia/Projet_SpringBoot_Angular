import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListePlanFrontComponent } from './liste-plan-front.component';

describe('ListePlanFrontComponent', () => {
  let component: ListePlanFrontComponent;
  let fixture: ComponentFixture<ListePlanFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListePlanFrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListePlanFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
