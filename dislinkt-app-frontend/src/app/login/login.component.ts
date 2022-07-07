import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private router : Router) {
  }

  ngOnInit(): void {
  }
  loginData = {username: "", password: ""}

  login() {
    if (this.loginData.username.trim() != "" && this.loginData.password.trim() != "") {
      axios.post(environment.api + '/users/login', this.loginData)
        .then(response => {
          if (response.data.role != "NO USER") {
            localStorage.setItem("id", response.data.id)
            localStorage.setItem("username", response.data.username)
            localStorage.setItem("role", response.data.role)
            localStorage.setItem("notificationsOn", response.data.notificationsOn)
            Swal.fire({
              icon: 'success',
              title: 'You have successfully logged in !',
              showConfirmButton: false,
              timer: 1500
            })
            this.router.navigate(['/home'])
           
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Your credentials are wrong. Please, try again.',
              showConfirmButton: false,
              timer: 1500
            })
          }
        })
        .catch(e => {
          Swal.fire({
            icon: 'error',
            title: 'Something went wrong. Please, try again later.',
            showConfirmButton: false,
            timer: 1500
          })
        })
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Fill out both username and password !',
        showConfirmButton: false,
        timer: 1500
      })
    }
  }

}
