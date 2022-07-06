import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-info-company',
  templateUrl: './info-company.component.html',
  styleUrls: ['./info-company.component.scss']
})
export class InfoCompanyComponent implements OnInit {

  isOwner : boolean = false
  state : string = 'jobs'

  constructor() { }

  ngOnInit(): void {
  }

}
