import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdddeliverybackComponent } from './adddeliveryback.component';

describe('AdddeliverybackComponent', () => {
  let component: AdddeliverybackComponent;
  let fixture: ComponentFixture<AdddeliverybackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdddeliverybackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdddeliverybackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
