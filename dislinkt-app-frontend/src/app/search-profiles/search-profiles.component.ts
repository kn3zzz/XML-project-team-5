import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import axios from 'axios';
import { Router } from '@angular/router';
import { ProfilesData } from './search-profiles.dto';
import { ConnectionsService } from '../services/connections.service';
import { ConnectionDTO } from '../connections/connection.dto';


@Component({
  selector: 'app-search-profiles',
  templateUrl: './search-profiles.component.html',
  styleUrls: ['./search-profiles.component.css']
})
export class SearchProfilesComponent implements OnInit {

  public connections: ConnectionDTO[]
  public profiles: ProfilesData[]
  query = ""

  constructor(private router: Router, private connectionsService: ConnectionsService) { 
    this.profiles = [];
    this.connections = [];

    this.GetConnections();
  }

  ngOnInit(): void {
    this.getAllProfiles();
  }

  search() {
    if (this.query != "") {
    axios.get(environment.api + '/users/search/' + this.query)
      .then(response => {
        this.profiles = []
        for(const p of (response.data as any)){
          this.profiles.push({
            "username": p.username,
            "name": p.name,
            "lastname": p.lastname,
            "biography" : p.biography,
            "interests": p.interests,
            "id": p.id,
            "privateProfile": p.privateProfile
          });
        }
        console.log(this.profiles)
      })
      .catch(e => {
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong. Please, try again.',
          showConfirmButton: false,
          timer: 2000
        })
      })
    } else {
      this.getAllProfiles();
    }
  }

  getAllProfiles() {
    axios.get(environment.api + '/users/profiles')
      .then(response => {
        this.profiles = []
        for(const p of (response.data as any)){
          this.profiles.push({
            "username": p.username,
            "name": p.name,
            "lastname": p.lastname,
            "biography" : p.biography,
            "interests": p.interests,
            "id": p.id,
            "privateProfile": p.privateProfile
          });
        }
        console.log(this.profiles)
      })
      .catch(e => {
        Swal.fire({
          icon: 'error',
          title: 'Something went wrong. Please, try again.',
          showConfirmButton: false,
          timer: 2000
        })
      })
  }

  sendFollow(id: number) {

    let state = this.GetProfile(id)?.privateProfile ? "PENDING" : "CONNECTED";
    this.AddConnection(id, state);
    
    
  }

  AddConnection(id: number, state: string){
    const body = {
      id: 2, //id is generated on backend, 2 is a joke.
      sender: localStorage.getItem("id"),
      receiver: id,
      connectionState: state
    }
    axios.post(environment.api + "/connections/addConnection", body).then(response => {
      if(response.data == true){
        Swal.fire({
          icon: 'success',
          title: 'Request sent.',
          showConfirmButton: false,
          timer: 1000
        })
        this.GetConnections();
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

  GetProfile(id: number): ProfilesData | undefined{
    return this.profiles.find(x => x.id == id);
  }

  GetConnections(){
    this.connectionsService.GetConnections(JSON.parse(localStorage.getItem("id") || "{}")).subscribe((data:any) =>{
      this.connections = [];
      for(const d of data){
        this.connections.push(d);
      }
      console.log(this.connections);
      
    });
  }

  ConnectionExists(id: number): boolean{
    
    for(const c of this.connections){
      if (c.receiver == id || c.sender == id)
        return true;
    }
    return false;
  }

  IsNotMe(id: number): boolean{
    if(id == JSON.parse(localStorage.getItem("id") || "{}"))
      return false;
    else return true;
  }
}
