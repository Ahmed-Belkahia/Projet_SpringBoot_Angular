import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddfurniturebackComponent } from './addfurnitureback.component';

describe('AddfurniturebackComponent', () => {
  let component: AddfurniturebackComponent;
  let fixture: ComponentFixture<AddfurniturebackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddfurniturebackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddfurniturebackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
