import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-set-new-password',
  templateUrl: './set-new-password.component.html',
  styleUrls: ['./set-new-password.component.css']
})
export class SetNewPasswordComponent implements OnInit {

  password!: string
  confirmPassword!: string
  checkPassword = new FormControl('', [Validators.required]);
  checkConfirmPassword = new FormControl('', [Validators.required]);
  errorMessage: string = ""
  errorMessage1: string = ""
  token!: string | null

  constructor(public activeRoute: ActivatedRoute, public authService: AuthService, public route: Router, public matSnackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  isButtonDisabled() {
    if (this.checkPassword.invalid || this.checkConfirmPassword.invalid || (this.password !== this.confirmPassword) ||
      !this.isFormDataValid())
      return true;

    return false;
  }

  passwordsMatch() {
    if (this.password !== this.confirmPassword)
      this.errorMessage = "Passwords don't match!"
    else
      this.errorMessage = ""
  }

  setPass() {
    this.token = this.activeRoute.snapshot.paramMap.get('token');

    this.authService.setPassword(this.password, this.token).subscribe(
      (data) => {
        this.matSnackBar.open("Password successfuly changed!!", 'Dismiss', {
          duration: 1000
        });

        setTimeout(() => {
          this.route.navigate(['/login'])
        }, 500);
      },
      (error) => {
        this.errorMessage = "Unable to reset password";
      }
    )
  }

  isFormDataValid(): boolean {
    var passwordPattern = new RegExp(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)/);

    if (this.password.length < 8) {
      this.errorMessage1 = "Password must be minimum 8 characters long";
      return false;
    }
    else if (!passwordPattern.test(this.password)) {
      this.errorMessage1 = "Password must have capital letters, lowercase as well as special characters";
      return false;
    }
    else {
      this.errorMessage1 = ""
    }
    return true;
  }

}
