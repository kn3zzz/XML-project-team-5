import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Company } from '../model/company';
import { CompanyService } from '../service/company.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-new-company',
  templateUrl: './new-company.component.html',
  styleUrls: ['./new-company.component.scss']
})
export class NewCompanyComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router,  private userService: UserService, private companyService: CompanyService) { }

  request: Company = new Company;
  email: any;
  allCompanies: Company[] = [];

  ngOnInit(): void {
    this.email = localStorage.getItem('user')
    this.userService.getCompanyByOwnerUsername(this.email!).subscribe(
      (data: any) => {
        console.log(data)
        if (data != null) {
          alert("User can send only one request!");
          this.router.navigate(['/user-home'])
        }
    })
      
    this.loadAllCompanies();
  }

  registration(){
    this.request.ownerEmail = this.email;

    if (this.request.name.trim() != "" && this.request.description.trim() != "" && this.request.phoneNumber.trim() != ""){
      
      // broj telefona pocinje znakom '+', a zatim mogu biti samo cifre
      if (this.request.phoneNumber[0] == "+"){
        let isnum = /^\d+$/.test(this.request.phoneNumber.substring(this.request.phoneNumber.indexOf('+') + 1));
        if (isnum) {
          if (this.nameIsUnique(this.request.name)){
            this.companyService.saveCompany(this.request).subscribe(
              (data: any) => {
                alert("Request for company registation sent!")
                this.router.navigate(['/user-home'])
              }
            )
          } else {
            alert("Company name must be unique!");
          }
       } else {
        alert("Wrong format for phone number!");
       }
      } else  {
        alert("Wrong format for phone number!");
      }
    } else {
      alert("All fields must be filled!");
    }
  }

  nameIsUnique(name: string){
    for(let c of this.allCompanies){
      if (c.name == name){
        return false;
      }
    }
    return true;
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
