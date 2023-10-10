import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeContratComponent } from './liste-contrat.component';

describe('ListeContratComponent', () => {
  let component: ListeContratComponent;
  let fixture: ComponentFixture<ListeContratComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeContratComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeContratComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
