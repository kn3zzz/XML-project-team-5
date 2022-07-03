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
    LikePost(reactionInfo:ReactionDTO,id:String) : Observable<any> {
        reactionInfo.userId = Number(localStorage.getItem('id'));
        const body = {
            postId:id,
            userId : reactionInfo.userId ,
          };
        return this.http.post<any>(this.url + '/likePost', body);
    }
    DislikePost(reactionInfo:ReactionDTO,id:String) : Observable<any> {
        reactionInfo.userId = Number(localStorage.getItem('id'));
        const body = {
            postId: id,
            userId : reactionInfo.userId ,
          };
        return this.http.post<any>(this.url + '/dislikePost', body);
    }
    CommentPost(commentInfo:CommentDTO,id:String) : Observable<any> {
        commentInfo.userId = Number(localStorage.getItem('id'));
        const body = {
            postId: id,
            userId : commentInfo.userId ,
            content:commentInfo.content,
            dateCreated:commentInfo.dateCreated
          };
        return this.http.post<any>(this.url + '/commentPost', body);
    }
  }
