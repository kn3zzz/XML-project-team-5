import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import { PostService } from '../services/post.service';
import { CreatePostDTO } from './post.dto';
import {CommentDTO} from './comment.dto'
import { PostInfoDTO } from './postInfo.dto';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  public newPost : CreatePostDTO;
  public newComment : CommentDTO;
  public allPosts : PostInfoDTO[];

  constructor(private postServise: PostService) {
    this.newPost = new CreatePostDTO();
    this.newComment = new CommentDTO();
    this.allPosts = [];
   }

  ngOnInit(): void {
    this.getPosts()
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
  reloadPage() {
    window.location.reload();
  }
  createPost():void{
    //this.newPost.postText = this.linkify(this.newPost.postText);
    this.postServise.CreatePost(this.newPost).subscribe((d:any) =>{
      alert('Post created');  
      this.getPosts()
      this.newPost.postText="";
    }) 
  }
  likePost(id:number):void{
    this.postServise.LikePost(Number(localStorage.getItem('id')),id).subscribe((d:any) =>{
      this.getPosts()

    })
  }
  dislikePost(id:number):void{
    this.postServise.DislikePost(Number(localStorage.getItem('id')),id).subscribe((d:any) =>{
      this.getPosts()

    })
  }
  commentPost(id:number):void{
    this.postServise.CommentPost(this.newComment,id).subscribe((d:any) =>{
      this.getPosts()
      this.newComment.content = "";

    })
  }

  getPosts() {
    this.postServise.GetFeed(0).subscribe((data:any) =>{
      this.allPosts = []
      for(const p of (data as any)){
        this.allPosts.push({
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

  handleFileSelect(evt: any){
    var files = evt.target.files;
    var file = files[0];

  if (files && file) {
      var reader = new FileReader();

      reader.onload =this._handleReaderLoaded.bind(this);

      reader.readAsBinaryString(file);
  
    }
    
}
_handleReaderLoaded(readerEvt: any) {
  var binaryString = readerEvt.target.result;
         this.newPost.imageString= "data:image/jpeg;base64," + btoa(binaryString);
         console.log(btoa(binaryString));
 }

linkify(text: string) : string{
  var urlRegex =/(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
  return text.replace(urlRegex, function(url) {
      return '<a href="' + url + '">' + url + '</a>';
  });
}

linkAndLog(text: string): string{
    console.log(this.linkify(text));
    return this.linkify(text);
}

splitByLink(textToCheck: string) {
 
  var expression = /(https?:\/\/)?[\w\-~]+(\.[\w\-~]+)+(\/[\w\-~@:%]*)*(#[\w\-]*)?(\?[^\s]*)?/gi;
  var regex = new RegExp(expression);
  var match; 
  var splitText = []; 
  var startIndex = 0;
  while ((match = regex.exec(textToCheck)) != null) {
          
    splitText.push({text: textToCheck.substring(startIndex, (match.index + startIndex)), type: 'text'});
                
    var cleanedLink = textToCheck.substring(match.index, (match.index + match[0].length));
    cleanedLink = cleanedLink.replace(/^https?:\/\//,'');
    splitText.push({text: cleanedLink, type: 'link'});
                  
    startIndex = match.index + (match[0].length);               
  }
  if (startIndex < textToCheck.length) splitText.push({text: textToCheck.substring(startIndex), type: 'text'});
  return splitText;
}


}
