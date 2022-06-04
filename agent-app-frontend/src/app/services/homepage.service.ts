import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable, observable } from 'rxjs';
import { PostDto } from '../homepage/postDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomepageService {

  constructor(private http: HttpClient) { }

  getPosts(): Observable<PostDto> {
    return this.http.get<any>(environment.backendAPI + '/post/getPosts');
}

}
