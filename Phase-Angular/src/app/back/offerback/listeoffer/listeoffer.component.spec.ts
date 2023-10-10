import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeofferComponent } from './listeoffer.component';

describe('ListeofferComponent', () => {
  let component: ListeofferComponent;
  let fixture: ComponentFixture<ListeofferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeofferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeofferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
