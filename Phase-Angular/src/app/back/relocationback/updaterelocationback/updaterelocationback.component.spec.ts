import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdaterelocationbackComponent } from './updaterelocationback.component';

describe('UpdaterelocationbackComponent', () => {
  let component: UpdaterelocationbackComponent;
  let fixture: ComponentFixture<UpdaterelocationbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdaterelocationbackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdaterelocationbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
