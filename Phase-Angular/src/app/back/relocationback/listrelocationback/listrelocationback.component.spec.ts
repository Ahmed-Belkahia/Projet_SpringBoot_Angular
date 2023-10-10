import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListrelocationbackComponent } from './listrelocationback.component';

describe('ListrelocationbackComponent', () => {
  let component: ListrelocationbackComponent;
  let fixture: ComponentFixture<ListrelocationbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListrelocationbackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListrelocationbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
