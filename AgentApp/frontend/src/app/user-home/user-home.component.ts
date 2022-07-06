import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Company } from '../model/company';
import { CompanyService } from '../service/company.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit {

  email: string = '';
  user: any;
  allCompanies: Company []

  constructor(private router: Router, private http: HttpClient, private companyService: CompanyService) { }


  ngOnInit(): void {
    this.loadAllCompanies();
  }


  loadAllCompanies(){
    this.companyService.getAll().subscribe(
      (data: Company[]) => {
        this.allCompanies = data;
        console.log(this.allCompanies)
      }
    )
  }
}
