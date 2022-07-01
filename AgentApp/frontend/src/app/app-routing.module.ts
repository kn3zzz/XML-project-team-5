import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TestComponent } from './test/test.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { NewAdminComponent } from './new-admin/new-admin.component';
import { AccountActivationComponent } from './account-activation/account-activation.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { PasswordRecoveryComponent } from './password-recovery/password-recovery.component';
import { RoleGuardService } from './service/role-guard.service';
import { NewCompanyComponent } from './new-company/new-company.component';
import { MyCompanyComponent } from './my-company/my-company.component';

const routes: Routes = [
  {path: "", redirectTo: '/login', pathMatch: 'full'},
  {path: "login", component: LoginComponent},
  {path: "registration", component: RegistrationComponent},
  {path: "admin-home", component: AdminHomeComponent, canActivate: [RoleGuardService], data: {expectedRole: ['ROLE_ADMIN']}},
  {path: "user-home", component: UserHomeComponent, canActivate: [RoleGuardService], data: {expectedRole: ['ROLE_USER', 'ROLE_COMPANY_OWNER']}},
  {path: "new-admin", component: NewAdminComponent, canActivate: [RoleGuardService], data: {expectedRole: ['ROLE_ADMIN']}},
  {path: "activate-account/:token", component: AccountActivationComponent},
  {path: "change-password", component: ChangePasswordComponent},
  {path: "recoverypass", component: ForgotPasswordComponent},
  {path: "new-company", component: NewCompanyComponent},
  {path: "my-company", component: MyCompanyComponent},
  {path: "activate-password/:token", component: PasswordRecoveryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
