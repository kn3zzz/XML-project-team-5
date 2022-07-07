import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import { ActivatedRoute, Router } from '@angular/router';
import { PostInfoDTO } from '../homepage/postInfo.dto';
import { UserDTO } from './user.dto';
import { PostService } from '../services/post.service';
import { UserService } from '../services/user.service';
import { CommentDTO } from '../homepage/comment.dto';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public id;
  public posts : PostInfoDTO[];
  public newComment : CommentDTO;
  public user : UserDTO;
  constructor(private route:ActivatedRoute,public router: Router,private postService:PostService, private userService : UserService ) {
    this.id = 0;
    this.posts = [];
    this.user = new UserDTO();
    this.newComment = new CommentDTO();
   }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getUser(this.id);
    this.getPosts(this.id) 
  }
  likePost(id:number):void{
    this.postService.LikePost(Number(localStorage.getItem('id')),id).subscribe((d:any) =>{
      this.getPosts(this.id)

    })
  }
  dislikePost(id:number):void{
    this.postService.DislikePost(Number(localStorage.getItem('id')),id).subscribe((d:any) =>{
      this.getPosts(this.id)

    })
  }
  commentPost(id:number):void{
    this.postService.CommentPost(this.newComment,id).subscribe((d:any) =>{
      this.getPosts(this.id)

    })
  }
  getUser(id:number){
    this.userService.GetUser(id).subscribe((data:any) => {
      this.user = { "name" : data.name,
                    "lastname" : data.lastname,
                    "username" : data.username,
                    "email" : data.email,
                    "phoneNumber" : data.phoneNumber,
                    "gender" : data.gender,
                    "birthDate" : data.birthDate,
                    "biography" : data.biography,
                    "workingExperience" : data.workingExperience,
                    "education" : data.education,
                    "skills" : data.skills,
                    "interests" : data.interests,
                    "privateProfile" : data.privateProfile,
                    "notificationsOn" : data.notificationsOn,
                    "id" : data.id
                  }
    })
  }
  getPosts(id:number){
    this.postService.GetPosts(id).subscribe((data:any) =>{
      this.posts = []
      for(const p of (data as any)){
        this.posts.push({
          "postId" : p.id,
          "username":p.username,
          "userId" : p.userId,
          "postText" : p.postText,
          "imageString" : p.imageString,
          "comments" : p.comments,
          "likedUsers" : p.likedPostUsers,
          "dislikedUsers": p.dislikedPostUsers,
          "dateCreated" : p.dateCreated
        });
      }
    });
  }

}
