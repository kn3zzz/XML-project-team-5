import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogJobsComponent } from './dialog-jobs.component';

describe('DialogJobsComponent', () => {
  let component: DialogJobsComponent;
  let fixture: ComponentFixture<DialogJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
