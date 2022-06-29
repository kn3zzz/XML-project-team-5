import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import axios from 'axios';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerData = {name: "", lastname: "", username: "", password: "", email: "", phoneNumber: "", gender: "Choose your gender*", birthDate: "", privateProfile: false}

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  register() {
    if (this.checkRegisterValidity()) {
      const isPrivate = document.getElementById('private') as HTMLInputElement | null;
      if (isPrivate?.checked)
        this.registerData.privateProfile = true
    axios.post(environment.api + '/users/addUser', this.registerData)
      .then(response => {
        if (response.data) {
        Swal.fire({
          icon: 'success',
          title: 'You have successfully registered. Now you can log in.',
          showConfirmButton: false,
          timer: 1500
        })
        this.router.navigate(['/login'])
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Username already taken. Please, pick another username',
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

checkRegisterValidity(): boolean {
  console.log(this.registerData)
  if (this.registerData.email.trim() == "" || this.registerData.name.trim() == "" || this.registerData.username.trim() == "" || 
      this.registerData.lastname.trim() == "" || this.registerData.birthDate.trim() == "" || this.registerData.phoneNumber.trim() == "" || this.registerData.gender.trim() == "Choose your gender*") {
        Swal.fire({
          icon: 'error',
          title: 'Please, fill all of the required fields.',
          showConfirmButton: true,
        })
        return false
      }
    return true
    }

}
