import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommentDTO } from '../homepage/comment.dto';
import { CreatePostDTO } from '../homepage/post.dto';
import { ReactionDTO } from '../homepage/reaction.dto';

@Injectable({
    providedIn: 'root'
  })
export class UserService {
    url: string;
    constructor (private http: HttpClient) {
        this.url = "http://localhost:8000/users";
    }

    GetUser(userId:Number) : Observable<any>{
        return this.http.get<any>(this.url + '/getUser/' + userId);
    }
}