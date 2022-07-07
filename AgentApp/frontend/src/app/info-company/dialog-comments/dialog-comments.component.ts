import { Component, OnInit } from '@angular/core';
import { Comment } from 'src/app/model/comment';
import { MyCompanyComponent } from 'src/app/my-company/my-company.component';
import { CommentService } from 'src/app/service/comment.service';

@Component({
  selector: 'app-dialog-comments',
  templateUrl: './dialog-comments.component.html',
  styleUrls: ['./dialog-comments.component.scss']
})
export class DialogCommentsComponent implements OnInit {

  comments: Comment[] = []

  constructor(private myComponent: MyCompanyComponent, private commentService: CommentService) { }

  ngOnInit(): void {
    this.loadCompanyComments()
  }

  loadCompanyComments() {
    this.commentService.getAllCompanyComments(this.myComponent.id).subscribe(res => this.comments = res)
  }

}
