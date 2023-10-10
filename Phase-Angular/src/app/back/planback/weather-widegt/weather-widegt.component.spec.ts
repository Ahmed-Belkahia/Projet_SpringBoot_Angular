import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherWidegtComponent } from './weather-widegt.component';

describe('WeatherWidegtComponent', () => {
  let component: WeatherWidegtComponent;
  let fixture: ComponentFixture<WeatherWidegtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WeatherWidegtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WeatherWidegtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
