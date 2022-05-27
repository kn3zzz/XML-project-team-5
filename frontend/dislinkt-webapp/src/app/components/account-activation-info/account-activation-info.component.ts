import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-account-activation-info',
  templateUrl: './account-activation-info.component.html',
  styleUrls: ['./account-activation-info.component.css']
})
export class AccountActivationInfoComponent implements OnInit {

  token!: string | null
  message!: string

  constructor(public activeRoute: ActivatedRoute, public authService: AuthService) { }

  ngOnInit(): void {
    this.token = this.activeRoute.snapshot.paramMap.get('token');
    this.authService.activateAccount(this.token).subscribe(
      (data) => {
        this.message = "Account successfully activated!"
      },
      (error) => {
        this.message = error.error.message
      }
    );
  }

}
