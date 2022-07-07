import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Comment } from '../model/comment';
import { CommentInterview } from '../model/commentInterview';
import { CommentSalary } from '../model/commentSalary';
import { Position } from '../model/position';
import { MyCompanyComponent } from '../my-company/my-company.component';
import { CommentService } from '../service/comment.service';

@Component({
  selector: 'app-info-company',
  templateUrl: './info-company.component.html',
  styleUrls: ['./info-company.component.scss']
})
export class InfoCompanyComponent implements OnInit {

  isOwner : boolean = false
  state : string = 'jobs'
  comment: Comment = new Comment
  comments: Comment[] = []
  positions: Position[] = []
  commentIterview: CommentInterview = new CommentInterview
  commentInterviews: CommentInterview[] = []
  commentSalary: CommentSalary = new CommentSalary

  titleFormControl = new FormControl('', [Validators.required]);
  positionFormControl = new FormControl('', [Validators.required]);
  contentFormControl = new FormControl('', [Validators.required]);
  starsFormControl = new FormControl('', [Validators.min(1)]);
  isFormerEmployeeFormControl = new FormControl(undefined, [Validators.required]);
  fairPayFormControl = new FormControl(undefined, [Validators.required]);
  starsError: boolean = false;

  rate : number = 0
  rates : number[] = [ 1, 2, 3, 4, 5]

  constructor(private myComponent: MyCompanyComponent, private commentService: CommentService) { }

  ngOnInit(): void {
    this.commentService.getAllPositionsByCompanyId(this.myComponent.id).subscribe(res => this.positions = res)
    if(localStorage.getItem('role') == "ROLE_COMPANY_OWNER"){
      this.isOwner = true;
    }
  }
  
  checkIfFormIsInvalid(){
    if(this.rate == 0) {
      this.starsError = true;
    }
    if(this.titleFormControl.hasError('required') 
      || this.contentFormControl.hasError('required') 
      || this.positionFormControl.hasError('required') 
      || this.starsError) {
      return true;
    } else {
      return false;
    }
  }

  saveComment(){
    this.comment.companyId = this.myComponent.id
    this.commentService.leaveComment(this.comment).subscribe( 
      data => {
        window.location.reload();
      }, 
      err => {
        alert(err.message);
      }
    );
  }

  saveCommentInterview(){
    this.commentIterview.companyId = this.myComponent.id
    this.commentService.leaveInterviewComment(this.commentIterview).subscribe( 
      data => {
        window.location.reload();
      }, 
      err => {
        alert(err.message);
      }
    );
  }

  saveCommentSalary(){
    this.commentSalary.companyId = this.myComponent.id
    this.commentService.leaveSalaryComment(this.commentSalary).subscribe( 
      data => {
        window.location.reload();
      }, 
      err => {
        console.log(err)
        alert(err.message);
      }
    );
  }
}
