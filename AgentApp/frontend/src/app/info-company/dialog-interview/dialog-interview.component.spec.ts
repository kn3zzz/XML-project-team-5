import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogInterviewComponent } from './dialog-interview.component';

describe('DialogInterviewComponent', () => {
  let component: DialogInterviewComponent;
  let fixture: ComponentFixture<DialogInterviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogInterviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogInterviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
