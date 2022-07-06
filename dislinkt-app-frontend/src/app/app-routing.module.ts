import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConnectionsComponent } from './connections/connections.component';
import { FollowRequestsComponent } from './follow-requests/follow-requests.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { MyInfoComponent } from './my-info/my-info.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { SearchProfilesComponent } from './search-profiles/search-profiles.component';
import { HttpClientModule } from '@angular/common/http';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent},
  { path: 'my-info', component: MyInfoComponent},
  { path: 'home', component: HomepageComponent},
  { path: 'follow-requests', component: FollowRequestsComponent},
  { path: 'search-profiles', component: SearchProfilesComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'connections', component: ConnectionsComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
