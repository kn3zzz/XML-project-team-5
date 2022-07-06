import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Company } from 'src/app/model/company';
import { JobOffer } from 'src/app/model/job-offer';
import { MyCompanyComponent } from 'src/app/my-company/my-company.component';
import { AuthService } from 'src/app/service/auth.service';
import { CompanyService } from 'src/app/service/company.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-dialog-jobs',
  templateUrl: './dialog-jobs.component.html',
  styleUrls: ['./dialog-jobs.component.scss']
})
export class DialogJobsComponent implements OnInit {

  jobOffers: JobOffer[] = [];
  myJobs: JobOffer[] = [];

  constructor(private companyService: CompanyService, private userService: UserService, private router: Router, private authService: AuthService, private myCompany: MyCompanyComponent) { }

  ngOnInit(): void {
    this.loadJobOffers();
  }

  loadJobOffers(){
    this.companyService.getAllJobOffers().subscribe(
      (data: JobOffer[]) => {
        this.jobOffers = data;

        for(let j of this.jobOffers){
          this.companyService.getCompanyById(j.companyId).subscribe(
            (data: Company) => {
              j.company = data;
          })
          
          if(j.companyId == this.myCompany.company.id){
            this.myJobs.push(j);
          }
        }
      }
    )
  }

}
