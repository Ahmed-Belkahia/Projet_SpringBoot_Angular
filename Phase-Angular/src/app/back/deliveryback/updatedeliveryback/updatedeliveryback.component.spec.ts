import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatedeliverybackComponent } from './updatedeliveryback.component';

describe('UpdatedeliverybackComponent', () => {
  let component: UpdatedeliverybackComponent;
  let fixture: ComponentFixture<UpdatedeliverybackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdatedeliverybackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatedeliverybackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
