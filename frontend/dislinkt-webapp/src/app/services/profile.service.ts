import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(public _http: HttpClient) { }

  getUser(): Observable<any> {
    return this._http.get<Observable<any>>('/api/whoami');
  }

  updateUser(user: any): Observable<any> {
    return this._http.put<Observable<any>>('http://localhost:5000/', user);
  }

  addNewExperience(experience:any): Observable<any> {
    return this._http.put<any>('/api/users/experience', experience)
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }

  addNewEducation(education:any): Observable<any> {
    return this._http.get<any>('http://localhost:5000/users/education', education)
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }

  addNewSkill(skill:any): Observable<any> {
    return this._http.get<any>('http://localhost:5000/users/skill', skill)
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }

  addNewInterest(interest:any): Observable<any> {
    return this._http.get<any>('http://localhost:5000/users/experience', interest)
    .pipe(
      tap(data => console.log("data: ", data))
    )
  }
}
