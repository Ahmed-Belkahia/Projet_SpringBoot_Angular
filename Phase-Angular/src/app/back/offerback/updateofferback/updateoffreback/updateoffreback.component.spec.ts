import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateoffrebackComponent } from './updateoffreback.component';

describe('UpdateoffrebackComponent', () => {
  let component: UpdateoffrebackComponent;
  let fixture: ComponentFixture<UpdateoffrebackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateoffrebackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateoffrebackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
