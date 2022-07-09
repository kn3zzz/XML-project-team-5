import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router : Router) {}

  canActivate() {
    if (localStorage.getItem('id') != null && localStorage.getItem('username') != null && localStorage.getItem('role') != null)
      return true;
    this.router.navigate(['login'])
    return false;
  }

  
}
