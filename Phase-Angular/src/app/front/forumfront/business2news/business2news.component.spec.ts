import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Business2newsComponent } from './business2news.component';

describe('Business2newsComponent', () => {
  let component: Business2newsComponent;
  let fixture: ComponentFixture<Business2newsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Business2newsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Business2newsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
