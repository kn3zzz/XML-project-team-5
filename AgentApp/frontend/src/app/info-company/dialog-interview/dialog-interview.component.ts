import { Component, OnInit } from '@angular/core';
import { CommentInterview } from 'src/app/model/commentInterview';
import { MyCompanyComponent } from 'src/app/my-company/my-company.component';
import { CommentService } from 'src/app/service/comment.service';

@Component({
  selector: 'app-dialog-interview',
  templateUrl: './dialog-interview.component.html',
  styleUrls: ['./dialog-interview.component.scss']
})
export class DialogInterviewComponent implements OnInit {

  constructor(private myComponent: MyCompanyComponent, private commentService: CommentService) { }

  commentInterviews: CommentInterview[] = []
  
  ngOnInit(): void {
    this.loadInterviewComments()
  }

  loadInterviewComments() {
    this.commentService.getAllInterviewCommentsByCompanyId(this.myComponent.id).subscribe(res => { this.commentInterviews = res})
  }
}
