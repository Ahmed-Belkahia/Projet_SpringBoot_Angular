import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FurniturefrontComponent } from './furniturefront.component';

describe('FurniturefrontComponent', () => {
  let component: FurniturefrontComponent;
  let fixture: ComponentFixture<FurniturefrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FurniturefrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FurniturefrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
