import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MyInfoComponent } from './my-info/my-info.component';
import { HomepageComponent } from './homepage/homepage.component';
import { FollowRequestsComponent } from './follow-requests/follow-requests.component';
import { SearchProfilesComponent } from './search-profiles/search-profiles.component';
import { ProfileComponent } from './profile/profile.component';
import { FormsModule } from '@angular/forms';
import { ConnectionsComponent } from './connections/connections.component';
import { HttpClientModule } from '@angular/common/http';
import { NotificationsComponent } from './notifications/notifications.component';
import { MessagingComponent } from './messaging/messaging.component';
import { JobOffersComponent } from './job-offers/job-offers.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    RegisterComponent,
    LoginComponent,
    MyInfoComponent,
    HomepageComponent,
    FollowRequestsComponent,
    SearchProfilesComponent,
    ProfileComponent,
    ConnectionsComponent,
    NotificationsComponent,
    MessagingComponent,
    JobOffersComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
