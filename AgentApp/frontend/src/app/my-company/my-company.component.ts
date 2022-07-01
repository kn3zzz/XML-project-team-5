import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Company } from '../model/company';
import { JobOffer } from '../model/job-offer';
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
  jobOffer: JobOffer = new JobOffer;
  promoteJobOffer: boolean = false;
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
  }

  saveJobOffer(){
    this.jobOffer.companyId = this.company.id;

    if (this.jobOffer.position.name.trim() != "" && this.jobOffer.position.pay != 0 && 
        this.jobOffer.dailyActivities.trim() != "" && 
        this.jobOffer.jobDescription.trim() != "") {

          var format = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
          let isnum = /^\d+$/.test(this.jobOffer.position.name);

          if (format.test(this.jobOffer.position.name) || isnum) { // provera da li 'name' polje sadrzi specijalni karakter ili broj
            alert("Position name can not include special characters or numbers!")
          } 
          else 
          {
            if (!isNaN(this.jobOffer.position.pay)) {   // provera da li je 'pay' polje tipa number
              if (this.jobOffer.position.pay >= 100) { 
                
                // if (this.promoteJobOffer) { // ukoliko se JobOffer promovise na Dislinktu
                //   if (this.apiToken.trim() != "" && this.apiToken != null) {
                //       let post = new DislinktPost;
                //       post.text = "Job Offer";

                //       post.jobOffer.companyId = this.jobOffer.companyId + ""
                //       post.jobOffer.dailyActivities = this.jobOffer.dailyActivities
                //       post.jobOffer.jobDescription = this.jobOffer.jobDescription
                //       post.jobOffer.preconditions = this.jobOffer.preconditions
                //       post.jobOffer.position.name = this.jobOffer.position.name
                //       post.jobOffer.position.pay = this.jobOffer.position.pay

                //       post.apiToken = this.apiToken;                  
                //       post.company.description =  this.company.description
                //       post.company.isActive =  this.company.isActive
                //       post.company.name =  this.company.name
                //       post.company.phoneNumber =  this.company.phoneNumber

                //       console.log(post)
                //       this.postService.addPost(post).subscribe(   // dodavanje JobOffer-a kao post u Dislinkt aplikaciji
                //       (data: any) => {
                //         console.log(data);
                //       });

                //       this.companyService.saveJobOffer(this.jobOffer).subscribe(  // cuvanje JobOffer-a u agentskoj bazi
                //       (data: any) => {
                //         alert("New job offer successfully created and promoted on Dislinkt!")
                //       });
                //   } 
                //   else 
                //   {
                //       alert("In order to promote your jo offer on Dislinkt, you must enter your API token!")
                //   }
                // } 
                // else 
                // { 
                  this.companyService.saveJobOffer(this.jobOffer).subscribe(  // cuvanje JobOffer-a u agentskoj bazi
                  (data: any) => {
                    alert("New job offer successfully created!")
                  });  
                //}

              } 
              else 
              {
                alert("Pay must be above 100!");
              }
            } 
            else 
            {
              alert("Pay field must be numeric value!");
            }
          }
    } 
    else 
    {
      alert("All fields must be filled!");
    }
  }

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
