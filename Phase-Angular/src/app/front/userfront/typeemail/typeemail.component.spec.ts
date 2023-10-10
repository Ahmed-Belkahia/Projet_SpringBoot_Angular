import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeemailComponent } from './typeemail.component';

describe('TypeemailComponent', () => {
  let component: TypeemailComponent;
  let fixture: ComponentFixture<TypeemailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TypeemailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeemailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
