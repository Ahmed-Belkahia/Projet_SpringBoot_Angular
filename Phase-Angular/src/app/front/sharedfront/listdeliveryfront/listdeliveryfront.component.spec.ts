import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListdeliveryfrontComponent } from './listdeliveryfront.component';

describe('ListdeliveryfrontComponent', () => {
  let component: ListdeliveryfrontComponent;
  let fixture: ComponentFixture<ListdeliveryfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListdeliveryfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListdeliveryfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
