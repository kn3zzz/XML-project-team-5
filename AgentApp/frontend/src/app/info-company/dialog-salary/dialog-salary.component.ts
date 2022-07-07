import { Component, OnInit } from '@angular/core';
import { CommentSalary } from 'src/app/model/commentSalary';
import { MyCompanyComponent } from 'src/app/my-company/my-company.component';
import { CommentService } from 'src/app/service/comment.service';

@Component({
  selector: 'app-dialog-salary',
  templateUrl: './dialog-salary.component.html',
  styleUrls: ['./dialog-salary.component.scss']
})
export class DialogSalaryComponent implements OnInit {

  constructor(private myComponent: MyCompanyComponent, private commentService: CommentService) { }

  salaryComments: CommentSalary[] = []

  ngOnInit(): void {
    this.loadSalaryComments()
  }

  loadSalaryComments() {
    this.commentService.getAllSalaryCommentsByCompanyId(this.myComponent.id).subscribe(res => { this.salaryComments = res})
  }
}
