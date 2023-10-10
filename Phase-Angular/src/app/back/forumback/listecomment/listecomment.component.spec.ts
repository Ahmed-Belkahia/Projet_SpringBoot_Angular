import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListecommentComponent } from './listecomment.component';

describe('ListecommentComponent', () => {
  let component: ListecommentComponent;
  let fixture: ComponentFixture<ListecommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListecommentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListecommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
