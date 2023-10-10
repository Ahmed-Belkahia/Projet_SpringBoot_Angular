import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContratParOffreFrontComponent } from './contrat-par-offre-front.component';

describe('ContratParOffreFrontComponent', () => {
  let component: ContratParOffreFrontComponent;
  let fixture: ComponentFixture<ContratParOffreFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContratParOffreFrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContratParOffreFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
