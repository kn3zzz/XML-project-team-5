import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { environment } from 'src/environments/environment';
import { ConnectionDTO } from './connection.dto';
import { ProfilesData } from '../search-profiles/search-profiles.dto'
import Swal from 'sweetalert2';
import { dashCaseToCamelCase } from '@angular/compiler/src/util';

@Component({
  selector: 'app-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.css']
})
export class ConnectionsComponent implements OnInit {

  userId: number
  connections: ConnectionDTO[]
  users: ProfilesData[]
  
  constructor(public router: Router) {
    this.userId = JSON.parse(localStorage.getItem("id") || "{}");
    this.connections = [];
    this.users = [];

    this.GetConnections();
    this.GetUsers();
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

          //manually adding some data DELETE LATER
          // this.connections.push({
          //   "id": 1,
          //   "sender": 4,
          //   "receiver": 2,
          //   "connectionState" : "CONNECTED",
          // });

          // this.connections.push({
          //   "id": 3,
          //   "sender": 3,
          //   "receiver": 2,
          //   "connectionState" : "PENDING",
          // });
          
          console.log(this.connections);
        });
  }

  GetUsers(){
    axios.get(environment.api + '/users/profiles')
        .then(response => {
          console.log(response.data);
          this.users = response.data;

          //manually adding some data DELETE LATER
          this.users.push({
            "username": "p.username4",
            "name": "p.name4",
            "lastname": "p.lastname4",
            "biography" : "p.biography4",
            "interests": "p.interests4",
            "id": 4,
          });
          this.users.push({
            "username": "p.username3",
            "name": "p.name3",
            "lastname": "p.lastname3",
            "biography" : "p.biography3",
            "interests": "p.interests3",
            "id": 3,
          });

          this.users.push({
            "username": "p.username1",
            "name": "p.name1",
            "lastname": "p.lastname1",
            "biography" : "p.biography1",
            "interests": "p.interests1",
            "id": 1,
          });
        });       
  }

  GetUserByConnection(connection: ConnectionDTO) : ProfilesData{
    for(const u of this.users){
      if((u.id != this.userId) && (u.id == connection.receiver || u.id == connection.sender)){
        return u;
      }
    }
    return new ProfilesData;
  }

  ChangeConnectionState(connection: ConnectionDTO, action: string){
    console.log("changing state");
    switch(action){
      case "unfollow":
        this.UpdateConnection(connection, "IDLE")
        this.ChangeConnectionLocally(connection, "IDLE")
        break;
      case "follow":
        this.UpdateConnection(connection, "PENDING")
        this.ChangeConnectionLocally(connection, "PENDING")
        break;
      case "unblock":
        this.UpdateConnection(connection, "CONNECTED")
        this.ChangeConnectionLocally(connection, "CONNECTED")
        break; 
      case "block":
        this.UpdateConnection(connection, "BLOCKED")
        this.ChangeConnectionLocally(connection, "BLOCKED")
        break;
      case "accept":
        this.UpdateConnection(connection, "CONNECTED")
        this.ChangeConnectionLocally(connection, "CONNECTED")
        break;
      case "reject":
        this.UpdateConnection(connection, "IDLE")
        this.ChangeConnectionLocally(connection, "IDLE")
        break;
      default:
        break;
    }
  }

  ChangeConnectionLocally(connection: ConnectionDTO, state: string){
    
    this.connections.forEach(function(conn){
      if(conn.id == connection.id){
        conn.connectionState = state
      }
    })
  }

  UpdateConnection(connection: ConnectionDTO, state: string){
    let body = {
      id: connection.id,
      sender: connection.sender,
      receiver: connection.receiver,
      connectionState: state
    }
    axios.put(environment.api + '/connections/update', body).then(response => {
      if(response.data == true){
        Swal.fire({
          icon: 'success',
          title: 'Done.',
          showConfirmButton: false,
          timer: 1500
        })

      }
      else{
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong',
          showConfirmButton: false,
          timer: 1500
        })
      }
    })
    
  }

  UnfollowConnection(connection: ConnectionDTO){
    console.log("unfollowing..");
    this.UpdateConnection(connection, "IDLE");
    this.ChangeConnectionLocally(connection, "IDLE");
  }

}
