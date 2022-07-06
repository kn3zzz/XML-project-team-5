import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  constructor(private http: HttpClient) { }

  private readonly headers = new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  });

  private readonly getAllByCompanyId = 'http://localhost:9000/comments/company/';
  private readonly getAllSalaryCommentsByCompanyIdPath = 'http://localhost:9000/comments/salary/company/';
  private readonly getAllInterviewCommentsByCompanyIdPath = 'http://localhost:9000/comments/interview/company/';
  private readonly getAllPositionsByCompanyIdPath = 'http://localhost:9000/comments/company/'; 
  private readonly leaveCommentPath = 'http://localhost:9000/comments'; 
  private readonly leaveSalaryCommentPath = 'http://localhost:9000/comments/salary'; 
  private readonly leaveInterviewCommentPath = 'http://localhost:9000/comments/interview'; 

  getAllCompanyComments(comanyId: number){
    return this.http.get<any>(`${this.getAllByCompanyId}` + comanyId);    
  }

  getAllSalaryCommentsByCompanyId(companyId: number) {
    return this.http.get<any>(`${this.getAllSalaryCommentsByCompanyIdPath}` + companyId)
  }

  getAllInterviewCommentsByCompanyId(companyId: number) {
    return this.http.get<any>(`${this.getAllInterviewCommentsByCompanyIdPath}` + companyId)
  }
  getAllPositionsByCompanyId(companyId: number) {
    return this.http.get<any>(`${this.getAllPositionsByCompanyIdPath}` + companyId + "/positions")
  }

  leaveComment(body){
    return this.http.post<any>(`${this.leaveCommentPath}`, JSON.stringify(body), {'headers': this.headers})
  }

  leaveSalaryComment(body){
    return this.http.post<any>(`${this.leaveSalaryCommentPath}`, JSON.stringify(body), {'headers': this.headers})
  }

  leaveInterviewComment(body){
    return this.http.post<any>(`${this.leaveInterviewCommentPath}`, JSON.stringify(body), {'headers': this.headers})
  }


}
