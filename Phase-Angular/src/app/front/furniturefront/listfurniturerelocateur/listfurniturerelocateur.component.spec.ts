import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListfurniturerelocateurComponent } from './listfurniturerelocateur.component';

describe('ListfurniturerelocateurComponent', () => {
  let component: ListfurniturerelocateurComponent;
  let fixture: ComponentFixture<ListfurniturerelocateurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListfurniturerelocateurComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListfurniturerelocateurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
