import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(public router: Router) { }

  ngOnInit(): void {
  }

}
