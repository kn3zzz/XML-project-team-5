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

          console.log(this.connections);
        });
  }

  GetUsers(){
    axios.get(environment.api + '/users/profiles')
        .then(response => {
          console.log(response.data);
          this.users = response.data;

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
      case "idle":
        this.DeleteConnection(connection)
        this.DeleteConnectionLocally(connection)
        break;
      case "follow":
        this.UpdateConnection(connection, "PENDING")
        this.ChangeConnectionLocally(connection, "PENDING")
        break;
      case "connect":
        this.UpdateConnection(connection, "CONNECTED")
        this.ChangeConnectionLocally(connection, "CONNECTED")
        break; 
      case "block":
        this.UpdateConnection(connection, "BLOCKED")
        this.ChangeConnectionLocally(connection, "BLOCKED")
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

  DeleteConnectionLocally(connection: ConnectionDTO){
    const index = this.connections.indexOf(connection);
    if(index !== -1){
      this.connections.splice(index, 1);
    }
  }

  UpdateConnection(connection: ConnectionDTO, state: string){
    const body = {
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
          timer: 1000
        })

      }
      else{
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong',
          showConfirmButton: false,
          timer: 1000
        })
      }
    })
    
  }

  UnfollowConnection(connection: ConnectionDTO){
    console.log("unfollowing..");
    this.UpdateConnection(connection, "IDLE");
    this.ChangeConnectionLocally(connection, "IDLE");
  }

  DeleteConnection(connection: ConnectionDTO){
    axios.delete(environment.api + "/connections/delete/" + connection.id).then(response => {
      if(response.data == true){
        
        Swal.fire({
          icon: 'success',
          title: 'Done.',
          showConfirmButton: false,
          timer: 1000
        })

      }
      else{
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong',
          showConfirmButton: false,
          timer: 1000
        })
      }
    })
  }

}
