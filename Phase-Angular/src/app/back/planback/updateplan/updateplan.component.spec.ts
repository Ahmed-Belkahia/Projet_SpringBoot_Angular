import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateplanComponent } from './updateplan.component';

describe('UpdateplanComponent', () => {
  let component: UpdateplanComponent;
  let fixture: ComponentFixture<UpdateplanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateplanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateplanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
