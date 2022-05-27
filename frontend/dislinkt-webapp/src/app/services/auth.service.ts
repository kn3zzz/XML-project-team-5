import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpStatusCode } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  login(user: any) {
    const body = {
      'username': user.username,
      'password': user.password
    };
    
    return this.http.post<any>('http://localhost:8000/login', body);
  }

  public logOut(): void {
    localStorage.removeItem('jwt');
  }

  register(user: any) {
    const body = {
      'username': user.username,
      'password': user.password,
      'email': user.email
    };
    return this.http.post("/register", JSON.stringify(body));
  }

  changePassword(req: any) {
    const body = {
      'username': 'petra',
      'oldPassword': req.oldPassword,
      'NewPassword': req.NewPassword,
    };
    return this.http.put("/api/auth/users/password", JSON.stringify(body));
  }

  forgotPassword(email: string) {
    const body = { 'email': email };
    return this.http.put("/api/auth/users/forgotten-password", JSON.stringify(body), { responseType: 'text' });
  }

  setPassword(password: string, token: string | null) {
    const body = {
      'token': token,
      'newPassword': password
    };

    return this.http.put('/api/auth/users/account-recovery', JSON.stringify(body), { responseType: 'text' });
  }

  activateAccount(token: string | null) {
    return this.http.get('/api/auth/activation/' + token);
  }

  passwordless(email: string) {
    const body = { 'email': email };
    return this.http.put("/api/auth/users/passwordless", JSON.stringify(body), { responseType: 'text' });
  }

  passwordlessLogin(token: string | null) {
    const body = { 'token': token };
    return this.http.put("/api/auth/session/passwordless", JSON.stringify(body)).pipe(
      map((res: any) => {
        console.log('Login success');
        localStorage.setItem('jwt', res.responseStatus);
        console.log(res);
      })
    )
  }

  getUser(): Observable<any> {
    return this.http.get<Observable<any>>('/api/whoami');
  }
}
