import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  username : string = "";
  email : string = "";
  password : string = "";
  repeatedPassword : string = "";
  name : string = "";
  surname : string = "";
  errorMessage : string = ""

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  submitForm() {
    if (this.isFormDataValid()){
      this.errorMessage = ''
      var user = {
        username : this.username,
        password : this.password,
        name : this.name,
        surname : this.surname,
        email : this.email
      }
      this.authService.register(user).subscribe((token: any) => {
        this.router.navigate(['']);
      },
      (error) => {
        this.errorMessage = error.error;
      }
      )
    }
  }

  isFormDataValid() : boolean {
    var emailPattern = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
    var passwordPattern = new RegExp(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)/);

    if (this.password !== this.repeatedPassword)
    {
      this.errorMessage = "Passwords do not match";
      return false;
    }
    if (this.password.length < 8)
    {
      this.errorMessage = "Password must be minimum 8 characters long";
      return false;
    }
    if (!emailPattern.test(this.email)){
      this.errorMessage = "Incorrect email format.";
      return false;
    }
    if (!passwordPattern.test(this.password)){
      this.errorMessage = "Incorrect password format.";
      return false;
    }
    return true;
  }

}
