import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import { Router } from '@angular/router';
import { MyInfoData } from './my-info.dto';

@Component({
  selector: 'app-my-info',
  templateUrl: './my-info.component.html',
  styleUrls: ['./my-info.component.css']
})
export class MyInfoComponent implements OnInit {

  public myInfo : MyInfoData
  response = {}
  constructor(private router : Router) {
    this.myInfo = new MyInfoData();
   }

  ngOnInit(): void {
    this.getUser()
  }

  getUser() {
      var id = localStorage.getItem('id');
      axios.get(environment.api + '/users/getUser/' + id)
        .then(response => {
          this.myInfo = response.data;
          console.log(this.myInfo)
        })
        .catch(e => {
          Swal.fire({
            icon: 'error',
            title: 'Something went wrong. Please, try again.',
            showConfirmButton: false,
            timer: 2000
          })
          this.router.navigate(['/login'])
        })
  }

  saveChanges() {
    if (this.checkUpdateValidity()) {
      const notificationsOn = document.getElementById('notifications') as HTMLInputElement | null;
      const isPrivate = document.getElementById('private') as HTMLInputElement | null;
      if (isPrivate?.checked)
        this.myInfo.privateProfile = true
      else
        this.myInfo.privateProfile = false
      if (notificationsOn?.checked)
        this.myInfo.notificationsOn = true
      else
        this.myInfo.notificationsOn = false
      console.log(this.myInfo.birthDate)
    axios.post(environment.api + '/users/updateUser', this.myInfo)
      .then(response => {
        this.response = response.data;
        Swal.fire({
          icon: 'success',
          title: 'Your info has been updated !',
          showConfirmButton: false,
          timer: 1500
        })
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

checkUpdateValidity(): boolean {
  if (this.myInfo.email.trim() == "" || this.myInfo.name.trim() == "" || this.myInfo.username.trim() == "" || 
      this.myInfo.lastname.trim() == "" || this.myInfo.birthDate.trim() == "" || this.myInfo.phoneNumber.trim() == "") {
        Swal.fire({
          icon: 'error',
          title: 'Some fields are required (name, username, lastname, email and birth date). Please, fill them, then save changes.',
          showConfirmButton: true,
        })
        return false
      }
    return true
    }


}
