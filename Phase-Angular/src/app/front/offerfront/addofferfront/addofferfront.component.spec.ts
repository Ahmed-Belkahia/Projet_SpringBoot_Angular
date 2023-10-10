import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddofferfrontComponent } from './addofferfront.component';

describe('AddofferfrontComponent', () => {
  let component: AddofferfrontComponent;
  let fixture: ComponentFixture<AddofferfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddofferfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddofferfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
