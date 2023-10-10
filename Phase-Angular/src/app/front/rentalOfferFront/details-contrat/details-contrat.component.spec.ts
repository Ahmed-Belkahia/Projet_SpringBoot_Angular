import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsContratComponent } from './details-contrat.component';

describe('DetailsContratComponent', () => {
  let component: DetailsContratComponent;
  let fixture: ComponentFixture<DetailsContratComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailsContratComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailsContratComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
