import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListecontractComponent } from './listecontract.component';

describe('ListecontractComponent', () => {
  let component: ListecontractComponent;
  let fixture: ComponentFixture<ListecontractComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListecontractComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListecontractComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
