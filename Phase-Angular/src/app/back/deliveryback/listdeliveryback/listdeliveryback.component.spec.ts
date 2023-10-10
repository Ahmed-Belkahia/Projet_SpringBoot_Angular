import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListdeliverybackComponent } from './listdeliveryback.component';

describe('ListdeliverybackComponent', () => {
  let component: ListdeliverybackComponent;
  let fixture: ComponentFixture<ListdeliverybackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListdeliverybackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListdeliverybackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
