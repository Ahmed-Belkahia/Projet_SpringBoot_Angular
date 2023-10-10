import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Business1newsComponent } from './business1news.component';

describe('Business1newsComponent', () => {
  let component: Business1newsComponent;
  let fixture: ComponentFixture<Business1newsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Business1newsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Business1newsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
