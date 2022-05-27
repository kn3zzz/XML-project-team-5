import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountActivationInfoComponent } from './account-activation-info.component';

describe('AccountActivationInfoComponent', () => {
  let component: AccountActivationInfoComponent;
  let fixture: ComponentFixture<AccountActivationInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountActivationInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountActivationInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
