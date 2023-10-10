import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddcontractComponent } from './addcontract.component';

describe('AddcontractComponent', () => {
  let component: AddcontractComponent;
  let fixture: ComponentFixture<AddcontractComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddcontractComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddcontractComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
