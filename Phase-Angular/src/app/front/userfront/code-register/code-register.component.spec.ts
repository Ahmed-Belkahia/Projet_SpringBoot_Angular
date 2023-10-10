import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeRegisterComponent } from './code-register.component';

describe('CodeRegisterComponent', () => {
  let component: CodeRegisterComponent;
  let fixture: ComponentFixture<CodeRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CodeRegisterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
