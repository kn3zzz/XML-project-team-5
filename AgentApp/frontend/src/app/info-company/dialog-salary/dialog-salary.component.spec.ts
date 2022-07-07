import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogSalaryComponent } from './dialog-salary.component';

describe('DialogSalaryComponent', () => {
  let component: DialogSalaryComponent;
  let fixture: ComponentFixture<DialogSalaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogSalaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogSalaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
