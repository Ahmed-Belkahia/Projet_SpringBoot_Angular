import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdddeliveryfrontComponent } from './adddeliveryfront.component';

describe('AdddeliveryfrontComponent', () => {
  let component: AdddeliveryfrontComponent;
  let fixture: ComponentFixture<AdddeliveryfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdddeliveryfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdddeliveryfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
