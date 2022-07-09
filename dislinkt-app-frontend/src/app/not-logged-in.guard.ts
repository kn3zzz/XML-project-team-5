import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotLoggedInGuard implements CanActivate {

  constructor(private router : Router){}

  canActivate() {
    if (localStorage.getItem('id') != null && localStorage.getItem('username') != null && localStorage.getItem('role') != null){
      this.router.navigate(['home'])
      return false;
    }
    return true;
  }
  
}
