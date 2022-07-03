import { Component, OnInit } from '@angular/core';
import { PostService } from '../services/post.service';
import { CreatePostDTO } from './post.dto';
import {CommentDTO} from './comment.dto'

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  public newPost : CreatePostDTO;
  public newComment : CommentDTO;

  constructor(private postServise: PostService) {
    this.newPost = new CreatePostDTO();
    this.newComment = new CommentDTO();
   }

  ngOnInit(): void {
  }

  imgSrc = "";

  onselectFile(e:any) {
    if(e.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload=(event:any) => {
        this.imgSrc = event.target.result;
      }
    }
  }

  removeImgSrc() {
    this.imgSrc = "";
  }
  createPost():void{
    this.postServise.CreatePost(this.newPost).subscribe((d:any) =>{
      alert('Post created');
    })
  }
  likePost(id:String):void{
    this.postServise.LikePost(this.newPost,id).subscribe((d:any) =>{
    })
  }
  dislikePost(id:String):void{
    this.postServise.DislikePost(this.newPost,id).subscribe((d:any) =>{
    })
  }
  commentPost(id:String):void{
    this.postServise.CommentPost(this.newComment,id).subscribe((d:any) =>{
    })
  }

}
