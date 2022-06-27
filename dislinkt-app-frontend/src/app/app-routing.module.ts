import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FollowRequestsComponent } from './follow-requests/follow-requests.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { MyInfoComponent } from './my-info/my-info.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent},
  { path: 'my-info', component: MyInfoComponent},
  { path: 'home', component: HomepageComponent},
  { path: 'follow-requests', component: FollowRequestsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
