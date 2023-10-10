import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListrelocationfrontComponent } from './listrelocationfront.component';

describe('ListrelocationfrontComponent', () => {
  let component: ListrelocationfrontComponent;
  let fixture: ComponentFixture<ListrelocationfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListrelocationfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListrelocationfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
