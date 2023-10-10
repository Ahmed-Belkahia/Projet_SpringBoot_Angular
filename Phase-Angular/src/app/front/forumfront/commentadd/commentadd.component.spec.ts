import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentaddComponent } from './commentadd.component';

describe('CommentaddComponent', () => {
  let component: CommentaddComponent;
  let fixture: ComponentFixture<CommentaddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentaddComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentaddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
