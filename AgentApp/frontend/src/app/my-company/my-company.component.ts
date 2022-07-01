import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Company } from '../model/company';
import { User } from '../model/user';
import { CompanyService } from '../service/company.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-my-company',
  templateUrl: './my-company.component.html',
  styleUrls: ['./my-company.component.scss']
})
export class MyCompanyComponent implements OnInit {
  user : User
  edit = true;
  company!: Company
  allCompanies: Company[] = [];

  constructor(private userService: UserService, private companyService: CompanyService) { }

  ngOnInit(): void {
    let username =  localStorage.getItem("user");

    if (username != undefined){
      this.userService.findByEmail(username).subscribe(
        (user: any) => {
        this.user = user;
        this.company = user.company
        console.log(user.company)
      })
    }

    this.loadAllCompanies
  }

  doEditPost(){
    if (this.company.name.trim() != "" && this.company.description.trim() != "" && this.company.phoneNumber.trim() != ""){

      if (this.company.phoneNumber[0] == "+"){
        let isnum = /^\d+$/.test(this.company.phoneNumber.substring(this.company.phoneNumber.indexOf('+') + 1));
        if (isnum) {
          if (this.nameIsUnique(this.company.name)){
             this.updateCompanyInfo()
           
          }
          else {
            alert("Company name must be unique!");
          }
        }
        else {
          alert("Wrong format for phone number!");
        }
      }else {
        alert("Wrong format for phone number!");
      }
    }
    else {
      alert("All fields must be filled!");
    }
  }

  updateCompanyInfo(){
    this.companyService.updateCompanyInfo(this.company).subscribe(
      (data: any) => {
        alert("Success")
        this.loadAllCompanies
        this.edit = !this.edit;
      }
    )
  }c

  doEdit(){
    this.edit = !this.edit;
  }

  nameIsUnique(name: string){
    for(let c of this.allCompanies){
      if (c.name == name && this.company.name !== name){
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
