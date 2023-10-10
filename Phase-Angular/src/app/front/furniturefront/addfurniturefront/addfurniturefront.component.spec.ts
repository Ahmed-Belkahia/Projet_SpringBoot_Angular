import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddfurniturefrontComponent } from './addfurniturefront.component';

describe('AddfurniturefrontComponent', () => {
  let component: AddfurniturefrontComponent;
  let fixture: ComponentFixture<AddfurniturefrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddfurniturefrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddfurniturefrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
