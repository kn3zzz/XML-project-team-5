import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDividerModule} from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule} from '@angular/material/toolbar'
import { MatMenuModule} from '@angular/material/menu'
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { FormsModule, ReactiveFormsModule, NgForm} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table'
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthGuardService } from './service/auth-guard.service';
import { RoleGuardService } from './service/role-guard.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TestComponent } from './test/test.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { NewAdminComponent } from './new-admin/new-admin.component';
import { HeaderComponent } from './header/header.component';
import { AccountActivationComponent } from './account-activation/account-activation.component';
import { TokenInterceptor } from './interceptor/token-interceptor';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { PasswordRecoveryComponent } from './password-recovery/password-recovery.component';
import { NewCompanyComponent } from './new-company/new-company.component';

@NgModule({
  declarations: [
    AppComponent,
    TestComponent,
    LoginComponent,
    RegistrationComponent,
    AdminHomeComponent,
    UserHomeComponent,
    NewAdminComponent,
    HeaderComponent,
    AccountActivationComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent,
    PasswordRecoveryComponent,
    NewCompanyComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDividerModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatToolbarModule,
    MatMenuModule,
    FormsModule,
    MatMenuModule, 
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
      }, 
      AuthGuardService,
      JwtHelperService,
      RoleGuardService, 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
