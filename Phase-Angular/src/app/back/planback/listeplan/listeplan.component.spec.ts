import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeplanComponent } from './listeplan.component';

describe('ListeplanComponent', () => {
  let component: ListeplanComponent;
  let fixture: ComponentFixture<ListeplanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeplanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeplanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
