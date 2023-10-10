import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddrelocationbackComponent } from './addrelocationback.component';

describe('AddrelocationbackComponent', () => {
  let component: AddrelocationbackComponent;
  let fixture: ComponentFixture<AddrelocationbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddrelocationbackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddrelocationbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
