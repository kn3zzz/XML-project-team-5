import { Component, OnInit } from '@angular/core'; 
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CompanyService } from '../service/company.service';
import { Company } from '../model/company';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss']
})
export class AdminHomeComponent implements OnInit {

  displayedColumns: string[] = ['name', 'description', 'phoneNumber', 'ownerEmail', 'approve', "reject", "state"];
  allCompanies: Company []

  constructor(private router: Router,
     private http: HttpClient,
     private companyService: CompanyService
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

    this.loadAllCompanies();
  }

  addAdmin() {
    this.router.navigate(['/new-admin'])
  }

  loadAllCompanies(){
    this.companyService.getAll().subscribe(
      (data: Company[]) => {
        this.allCompanies = data;
        console.log(this.allCompanies)
      }
    )
  }

  approve(id: number){
    this.companyService.approveRequest(id).subscribe(
      (data: any) => {
        this.loadAllCompanies();
        alert("Success")
      })
  }

  reject(id: number){
    this.companyService.rejectRequest(id).subscribe(
      (data: any) => {
        this.loadAllCompanies();
        alert("Success")
      })
  }
}
