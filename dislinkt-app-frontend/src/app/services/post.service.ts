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
export class PostService {
    url: string;
    constructor (private http: HttpClient) {
        this.url = "http://localhost:8000/posts";
    }
    
    CreatePost(postInfo:CreatePostDTO): Observable<any> {
        postInfo.userId = Number(localStorage.getItem('id'));
        console.log(postInfo.dateCreated);
      const body = {
        postId: postInfo.postId,
        userId : postInfo.userId ,
        postText : postInfo.postText,
        imageString : postInfo.imageString,
        dateCreated : postInfo.dateCreated
      };
      console.log(body);
      return this.http.post<any>(this.url + '/createPost', body);
    }
    LikePost(userId:Number,id:Number) : Observable<any> {
        const body = {
            postId:id,
            userId : userId ,
          };
        return this.http.post<any>(this.url + '/likePost', body);
    }
    DislikePost(userId:Number,id:Number) : Observable<any> {
      console.log(userId)
        const body = {
            postId: id,
            userId : userId ,
          };
        return this.http.post<any>(this.url + '/dislikePost', body);
    }
    CommentPost(commentInfo:CommentDTO,id:Number) : Observable<any> {
        commentInfo.userId = Number(localStorage.getItem('id'));
        const body = {
            postId: id,
            userId : commentInfo.userId ,
            content:commentInfo.content,
            dateCreated:commentInfo.dateCreated
          };
        return this.http.post<any>(this.url + '/commentPost', body);
    }
    GetFeed(id:Number): Observable<any> {
     id = Number(localStorage.getItem('id'))
      return this.http.get<any>(this.url + '/getFeed/' + id);
    }
    GetPosts(id:Number): Observable<any> {
      id = Number(localStorage.getItem('id'))
       return this.http.get<any>(this.url + '/getPosts/' + id);
     }
  }
