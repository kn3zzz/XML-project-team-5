import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  email!: string
  checkMail = new FormControl('', [Validators.email]);

  constructor(public authService: AuthService, public matSnackBar: MatSnackBar, public router: Router) { }

  ngOnInit(): void {
  }

  forgotPassword() {
    this.authService.forgotPassword(this.email).subscribe((data) => {
      this.matSnackBar.open("Email successfully sent!", 'Dismiss', {
        duration: 1000
      });

      setTimeout(() => {
        this.router.navigate(['/login'])
      }, 500);
    },
      (error) => {
        this.matSnackBar.open("An error occured while sending email!", 'Dismiss', {
          duration: 1000
        });
      }
    );
  }

}
