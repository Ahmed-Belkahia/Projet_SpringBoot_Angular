import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddcontractfrontComponent } from './addcontractfront.component';

describe('AddcontractfrontComponent', () => {
  let component: AddcontractfrontComponent;
  let fixture: ComponentFixture<AddcontractfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddcontractfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddcontractfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
