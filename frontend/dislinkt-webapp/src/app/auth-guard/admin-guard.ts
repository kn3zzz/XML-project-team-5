import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { User } from "../model/user";
import { AuthService } from "../services/auth.service";

@Injectable({
    providedIn: 'root'
  })
export class AdminGuard implements CanActivate{

    currentUser!: any

  constructor(
    private router: Router,
    private authenticationService: AuthService
) {
}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (localStorage.getItem('jwt') != null) {
        return new Promise(async (resolve, reject) => {
            this.authenticationService.getUser().toPromise().then((response) => {
                this.currentUser = response
                console.log(response)
                if (this.currentUser.user.role === 'ADMIN') {
                    resolve(true);
                    return true;
                }
                else {
                    this.router.navigate(['/login']);
                    resolve(false);
                    return false;
                }
            })
        })
    }
    this.router.navigate(['/login']);
    return false;
  }
}
