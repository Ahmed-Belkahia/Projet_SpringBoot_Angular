import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddrelocationfrontComponent } from './addrelocationfront.component';

describe('AddrelocationfrontComponent', () => {
  let component: AddrelocationfrontComponent;
  let fixture: ComponentFixture<AddrelocationfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddrelocationfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddrelocationfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
