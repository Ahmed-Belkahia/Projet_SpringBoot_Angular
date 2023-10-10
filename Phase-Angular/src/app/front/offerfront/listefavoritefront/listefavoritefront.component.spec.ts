import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListefavoritefrontComponent } from './listefavoritefront.component';

describe('ListefavoritefrontComponent', () => {
  let component: ListefavoritefrontComponent;
  let fixture: ComponentFixture<ListefavoritefrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListefavoritefrontComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListefavoritefrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
