import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Comment } from '../model/comment';
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

  titleFormControl = new FormControl('', [Validators.required]);
  positionFormControl = new FormControl('', [Validators.required]);
  contentFormControl = new FormControl('', [Validators.required]);
  starsFormControl = new FormControl('', [Validators.min(1)]);
  starsError: boolean = false;

  rate : number = 0
  rates : number[] = [ 1, 2, 3, 4, 5]

  constructor(private myComponent: MyCompanyComponent, private commentService: CommentService) { }

  ngOnInit(): void {
    this.loadCompanyComments()
    this.commentService.getAllPositionsByCompanyId(this.myComponent.id).subscribe(res => this.positions = res)
  }

  loadCompanyComments() {
    this.commentService.getAllCompanyComments(this.myComponent.id).subscribe(res => this.comments = res)
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
        this.loadCompanyComments();
      }, 
      err => {
        alert(err.message);
      }
    );
  }
}
