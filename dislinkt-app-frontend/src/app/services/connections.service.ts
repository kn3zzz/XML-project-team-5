import { HttpClient } from "@angular/common/http"
import { Injectable } from "@angular/core"
import axios from "axios"
import { Observable } from "rxjs"
import { environment } from "src/environments/environment"
import Swal from "sweetalert2"
import { ConnectionDTO } from "../connections/connection.dto"

@Injectable({
    providedIn: 'root'
  })
export class ConnectionsService{

    constructor (private http: HttpClient){}

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

    GetConnections(userId: number) : Observable<any> {
        return this.http.get<any>(environment.api + "/connections/getConnections/" + userId)
    }
    
    
}