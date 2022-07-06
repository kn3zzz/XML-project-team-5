import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import axios from 'axios';
import { Router } from '@angular/router';
import { NotificationData } from './notifications.dto';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  public notifications: NotificationData[]
  constructor(private router: Router) {
    this.notifications = []
   }

  ngOnInit(): void {

    this.getNotifications()
  }

  getNotifications() {
    const id = localStorage.getItem('id');
    axios.get(environment.api + '/notifications/getNotifications/' + id)
      .then(response => {
        this.notifications = []
        for(const p of (response.data as any)){
          this.notifications.push({
            "notificationsId": p.notificationsId,
            "text": p.text,
            "dateCreated": p.dateCreated,
            "seen" : p.seen,
          });
        }
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

  markAsRead(id: number) {
    const body = {
      "notificationId": id,
      "userId": localStorage.getItem('id')
    }
    axios.post(environment.api + '/notifications/markAsRead', body)
      .then(response => {
        this.getNotifications()
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

  delete(id: number) {
    const body = {
      "notificationId": id,
      "userId": localStorage.getItem('id')
    }
    axios.post(environment.api + '/notifications/delete', body)
      .then(response => {

        this.getNotifications()
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

}
