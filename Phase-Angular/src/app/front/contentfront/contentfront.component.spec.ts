import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentfrontComponent } from './contentfront.component';

describe('ContentfrontComponent', () => {
  let component: ContentfrontComponent;
  let fixture: ComponentFixture<ContentfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentfrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
