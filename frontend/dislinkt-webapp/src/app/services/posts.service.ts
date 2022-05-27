import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Comment, Post } from '../model/post';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private http: HttpClient) { }

  public createNewPost(post : FormData){
    return this.http
      .post(
        '/api/posts/new-post',
        post, { observe: 'response', responseType: 'text' });
  }

  public commentPost(comment : Comment, id : string){
    return this.http
      .post(
        '/api/posts/new-comment/'+ encodeURIComponent(id),
        comment, { observe: 'response', responseType: 'text' });
  }

  public getAllPosts() : Observable<Post[]> {
   return this.http.get<Post[]>('/api/posts');
  }

  public likePost(id : string) : Observable<Post> {
    return this.http.get<Post>('/api/posts/like/' + encodeURIComponent(id));
  }

  public dislikePost(id : string) : Observable<Post> {
    return this.http.get<Post>('/api/posts/dislike/' + encodeURIComponent(id));
  }

}
