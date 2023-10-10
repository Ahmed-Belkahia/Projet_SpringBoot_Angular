import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListecontractfrontComponent } from './listecontractfront.component';

describe('ListecontractfrontComponent', () => {
  let component: ListecontractfrontComponent;
  let fixture: ComponentFixture<ListecontractfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListecontractfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListecontractfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
