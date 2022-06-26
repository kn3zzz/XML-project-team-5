import { Component, OnInit } from '@angular/core'; 
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss']
})
export class AdminHomeComponent implements OnInit {

  displayedColumns: string[] = ['subject', 'validPeriod', 'viewCert', 'download', 'state', "revoke"];


  constructor(private router: Router,
     private http: HttpClient
     ) { }

  ngOnInit(): void { 

    let role = localStorage.getItem('role');
    if (role == "ROLE_USER"){
      this.router.navigate(['/user-home'])
      return;
    } 
    else if (role != "ROLE_USER" && role!= "ROLE_ADMIN"){
      this.router.navigate(['/login'])
      return;
    }
  }

  addAdmin() {
    this.router.navigate(['/new-admin'])
  }

}
