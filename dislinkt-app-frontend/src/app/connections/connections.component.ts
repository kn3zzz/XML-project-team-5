import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { environment } from 'src/environments/environment';
import { ConnectionDTO } from './connection.dto';

@Component({
  selector: 'app-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.css']
})
export class ConnectionsComponent implements OnInit {

  userId: number
  connections: ConnectionDTO[]
  constructor(public router: Router) {
    this.userId = JSON.parse(localStorage.getItem("id") || "{}");
    this.connections = [];

    this.GetConnections();
    console.log(this.userId);
   }

  ngOnInit(): void {
    this.userId = JSON.parse(localStorage.getItem("id") || "{}");
    this.GetConnections();

  }

  GetConnections(){
    axios.get(environment.api + '/connections/getConnections/' + this.userId)
        .then(response => {
          console.log(response.data);
          this.connections = response.data;
        });
  }

}
