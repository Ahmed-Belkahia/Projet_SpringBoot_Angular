import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarfrontComponent } from './navbarfront.component';

describe('NavbarfrontComponent', () => {
  let component: NavbarfrontComponent;
  let fixture: ComponentFixture<NavbarfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
