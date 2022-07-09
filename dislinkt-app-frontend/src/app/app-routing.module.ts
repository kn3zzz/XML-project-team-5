import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConnectionsComponent } from './connections/connections.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { MyInfoComponent } from './my-info/my-info.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { SearchProfilesComponent } from './search-profiles/search-profiles.component';
import { HttpClientModule } from '@angular/common/http';
import { MessagingComponent } from './messaging/messaging.component';
import { JobOffersComponent } from './job-offers/job-offers.component';
import { AuthGuard } from './auth.guard';
import { NotLoggedInGuard } from './not-logged-in.guard';

const routes: Routes = [
  { path: 'register', component: RegisterComponent, canActivate: [NotLoggedInGuard] },
  { path: 'login', component: LoginComponent, canActivate: [NotLoggedInGuard] },
  { path: 'my-info', component: MyInfoComponent,  canActivate: [AuthGuard]},
  { path: 'home', component: HomepageComponent, canActivate: [AuthGuard]},
  { path: 'search-profiles', component: SearchProfilesComponent},
  { path: 'profile/:id', component: ProfileComponent},
  { path: 'connections', component: ConnectionsComponent,  canActivate: [AuthGuard]},
  { path: 'notifications', component: NotificationsComponent,  canActivate: [AuthGuard]},
  { path: 'messaging', component: MessagingComponent,  canActivate: [AuthGuard]},
  { path: 'job-offers', component: JobOffersComponent,  canActivate: [AuthGuard]},
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login', pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
