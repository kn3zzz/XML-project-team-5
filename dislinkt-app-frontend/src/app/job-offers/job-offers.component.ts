import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import axios from 'axios';
import { JobOfferDTO } from './job-offer.dto';
import { Router } from '@angular/router';


@Component({
  selector: 'app-job-offers',
  templateUrl: './job-offers.component.html',
  styleUrls: ['./job-offers.component.css']
})
export class JobOffersComponent implements OnInit {

  query = ""
  public jobOffers: JobOfferDTO[]
  newOffer = {company: "", position: "", jobDescription: "", dailyActivities: "", preconditions: ""}

  constructor(private router: Router) {
    this.jobOffers = []
   }

  ngOnInit(): void {
    this.getAllJobOffers();
  }

  search() {
    if (this.query != "") {
    axios.get(environment.api + '/jobOffers/search/' + this.query)
      .then(response => {
        this.jobOffers = []
        for(const p of (response.data as any)){
          this.jobOffers.push({
            "company": p.company,
            "position": p.position,
            "jobDescription": p.jobDescription,
            "dailyActivities" : p.dailyActivities,
            "preconditions": p.preconditions,
          });
        }
        console.log(this.jobOffers)
      })
      .catch(e => {
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong. Please, try again.',
          showConfirmButton: false,
          timer: 2000
        })
      })
    } else {
      this.getAllJobOffers();
    }
  }

  getAllJobOffers() {
    axios.get(environment.api + '/jobOffers/getAllOffers')
      .then(response => {
        this.jobOffers = []
        for(const p of (response.data as any)){
          this.jobOffers.push({
            "company": p.company,
            "position": p.position,
            "jobDescription": p.jobDescription,
            "dailyActivities" : p.dailyActivities,
            "preconditions": p.preconditions,
          });
        }
        console.log(this.jobOffers)
      })
      .catch(e => {
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong. Please, try again.',
          showConfirmButton: false,
          timer: 2000
        })
      })
  }

  createJobOffer() {
    if (this.checkValidity()) {
    axios.post(environment.api + '/jobOffers/addJobOffer', this.newOffer)
      .then(response => {
        if (response.data) {
          this.getAllJobOffers();
        Swal.fire({
          icon: 'success',
          title: 'You have successfully added new job offer !',
          showConfirmButton: false,
          timer: 1500
        })
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong. Please, try again.',
          showConfirmButton: false,
          timer: 1500
        })
      }
      })
      .catch(e => {
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong. Please, try again.',
          showConfirmButton: false,
          timer: 1500
        })
        this.router.navigate(['/home'])
      })
    }
}

checkValidity(): boolean {
  if (this.newOffer.company.trim() == "" || this.newOffer.dailyActivities.trim() == "" || this.newOffer.jobDescription.trim() == "" || 
      this.newOffer.preconditions.trim() == "" || this.newOffer.position.trim() == "") {
        Swal.fire({
          icon: 'error',
          title: 'Please, fill all of the fields.',
          showConfirmButton: false,
          timer: 1500
        })
        return false
      }
    return true
    }

}
