import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeofferfavoriteComponent } from './listeofferfavorite.component';

describe('ListeofferfavoriteComponent', () => {
  let component: ListeofferfavoriteComponent;
  let fixture: ComponentFixture<ListeofferfavoriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListeofferfavoriteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeofferfavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
