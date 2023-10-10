import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListfurniturebackComponent } from './listfurnitureback.component';

describe('ListfurniturebackComponent', () => {
  let component: ListfurniturebackComponent;
  let fixture: ComponentFixture<ListfurniturebackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListfurniturebackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListfurniturebackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
