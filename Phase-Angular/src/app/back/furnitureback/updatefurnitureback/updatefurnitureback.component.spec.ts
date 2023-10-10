import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatefurniturebackComponent } from './updatefurnitureback.component';

describe('UpdatefurniturebackComponent', () => {
  let component: UpdatefurniturebackComponent;
  let fixture: ComponentFixture<UpdatefurniturebackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdatefurniturebackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatefurniturebackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
