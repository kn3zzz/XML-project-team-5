import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Post, Comment } from 'src/app/model/post';
import { AuthService } from 'src/app/services/auth.service';
import { PostsService } from 'src/app/services/posts.service';

@Component({
  selector: 'app-posts-view',
  templateUrl: './posts-view.component.html',
  styleUrls: ['./posts-view.component.css']
})
export class PostsViewComponent implements OnInit {
  posts!: Post[];
  liked!: string[];
  disliked!: string[];
  commentText!: string;
  commentOnPost!: string;
  comments!: Comment[]
  token!: string | null
  passwordless!: boolean

  constructor(public service: PostsService, private _snackBar: MatSnackBar, public activeRoute: ActivatedRoute,
    public authService: AuthService, public router: Router) { }

  ngOnInit(): void {
    this.token = this.activeRoute.snapshot.paramMap.get('token');
    this.passwordless = false;
    if (this.token != null) {
      this.passwordless = true;
      this.authService.passwordlessLogin(this.token).subscribe(
        (data) => {
          this._snackBar.open('Passwordless login successful', 'Dissmiss', {
            duration: 2000
          });

          setTimeout(() => {
            window.location.href = '/posts'
          }, 700);
        },
        (error) => {
          this._snackBar.open(error.error.message, 'Dissmiss', {
            duration: 3000
          });

          setTimeout(() => {
            window.location.href = '/login'
          }, 700);
        }
      );
    }

    this.commentText = ''
    this.service.getAllPosts().subscribe(res => this.posts = res);
  }

  likePost(id: string) {
    this.service.likePost(id).subscribe(res => {
      this.service.getAllPosts().subscribe(res => this.posts = res);
    });
  }

  dislikePost(id: string) {
    this.service.dislikePost(id).subscribe(res => {
      this.service.getAllPosts().subscribe(res => this.posts = res);
    });
  }

  commentPost() {
    let newComment = new Comment();
    newComment = {
      Username: "",
      Text: this.commentText
    }
    this.service.commentPost(newComment, this.commentOnPost).subscribe(
      (data) => {
        this._snackBar.open('Your comment has been submited.', 'Dissmiss', {
          duration: 3000
        });

        setTimeout(() => {
        }, 1000);
      },
      (error) => {
        this._snackBar.open('Commenting failed', 'Dissmiss', {
          duration: 3000
        });
      });;;
  }
}
