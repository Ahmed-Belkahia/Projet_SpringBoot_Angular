import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeofferfrontComponent } from './listeofferfront.component';

describe('ListeofferfrontComponent', () => {
  let component: ListeofferfrontComponent;
  let fixture: ComponentFixture<ListeofferfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeofferfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeofferfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
