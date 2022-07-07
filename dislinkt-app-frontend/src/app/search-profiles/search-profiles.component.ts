import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import axios from 'axios';
import { Router } from '@angular/router';
import { ProfilesData } from './search-profiles.dto';


@Component({
  selector: 'app-search-profiles',
  templateUrl: './search-profiles.component.html',
  styleUrls: ['./search-profiles.component.css']
})
export class SearchProfilesComponent implements OnInit {

  public profiles: ProfilesData[]
  query = ""

  constructor(private router: Router) { 
    this.profiles = [];
  }

  ngOnInit(): void {
    this.getAllProfiles();
  }

  search() {
    if (this.query != "") {
    axios.get(environment.api + '/users/search/' + this.query)
      .then(response => {
        this.profiles = []
        for(const p of (response.data as any)){
          this.profiles.push({
            "username": p.username,
            "name": p.name,
            "lastname": p.lastname,
            "biography" : p.biography,
            "interests": p.interests,
            "id": p.id,
            "privateProfile": p.privateProfile
          });
        }
        console.log(this.profiles)
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
      this.getAllProfiles();
    }
  }

  getAllProfiles() {
    axios.get(environment.api + '/users/profiles')
      .then(response => {
        this.profiles = []
        for(const p of (response.data as any)){
          this.profiles.push({
            "username": p.username,
            "name": p.name,
            "lastname": p.lastname,
            "biography" : p.biography,
            "interests": p.interests,
            "id": p.id,
            "privateProfile": p.privateProfile
          });
        }
        console.log(this.profiles)
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

  sendFollow(id: number) {
    const body = {
      id: 2, //id is generated on backend, 2 is a joke.
      sender: localStorage.getItem("id"),
      receiver: id,
      connectionState: "PENDING"
    }
    axios.post(environment.api + "/connections/addConnection", body).then(response => {
      if(response.data == true){
        Swal.fire({
          icon: 'success',
          title: 'Request sent.',
          showConfirmButton: false,
          timer: 500
        })

      }
      else{
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong',
          showConfirmButton: false,
          timer: 1500
        })
      }
    })
  }
}
