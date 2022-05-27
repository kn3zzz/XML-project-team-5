import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  logged : boolean = false

  constructor(public authService : AuthService) { }

  ngOnInit(): void {
    if (localStorage.getItem('jwt') != null)
      this.logged = true
  }

  logOut() {
    this.authService.logOut()
    this.logged = false
    window.location.reload()
  }

}
