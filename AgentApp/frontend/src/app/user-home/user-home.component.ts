import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit {

  email: string = '';
  user: any;

  displayedColumns: string[] = ['subject', 'validPeriod', 'viewCert', 'download', 'state', "revoke"];


  constructor(private router: Router, private http: HttpClient) { }


  ngOnInit(): void {

    let role = localStorage.getItem('role');
    if (role == "ROLE_ADMIN"){
      this.router.navigate(['/admin-home'])
      return;
    } 
    else if (role != "ROLE_USER" && role!= "ROLE_ADMIN"){
      this.router.navigate(['/login'])
      return;
    }

    this.email = localStorage.getItem('user') || ""
    this.http.get('http://localhost:9000/api/users/getByEmail/' + this.email)
    .subscribe(data => {
      this.user = data
      this.email = this.user.email
    })

  }
}
