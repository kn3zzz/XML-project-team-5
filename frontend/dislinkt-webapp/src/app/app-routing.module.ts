import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AccountActivationInfoComponent } from './components/account-activation-info/account-activation-info.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { SetNewPasswordComponent } from './components/set-new-password/set-new-password.component';
import { AdminGuard } from './auth-guard/admin-guard';
import { UserGuard } from './auth-guard/user-guard';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { HomepageComponent } from './components/homepage/homepage.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'home',
    component: HomepageComponent,
    canActivate: [UserGuard]
  },
  {
    path: '',
    component: HomepageComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'set-password/:token',
    component: SetNewPasswordComponent
  },
  {
    path: 'account-activation/:token',
    component: AccountActivationInfoComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
