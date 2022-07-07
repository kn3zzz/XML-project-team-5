import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'
import axios from 'axios';
import { MessagesUserDTO } from './message-user.dto';
import { MessageDTO } from './message.dto';

@Component({
  selector: 'app-messaging',
  templateUrl: './messaging.component.html',
  styleUrls: ['./messaging.component.css']
})
export class MessagingComponent implements OnInit {

  public users: MessagesUserDTO[]
  public messages: MessageDTO[]
  messageText = ""
  messagesSizeBefore = 0
  messagesSizeAfter = 0
  selectedUser = 0
  firstTime = true;
  constructor() {
    this.users = []
    this.messages = []
   }

  ngOnInit(): void {
    this.getMessagesUsers()
    setInterval(()=> { this.getMessages(this.selectedUser); }, 5 * 1000);
    
  }

  getMessagesUsers() {
    const id = localStorage.getItem('id');
    axios.get(environment.api + '/users/getMessagesUsers/' + id)
      .then(response => {
        this.users = []
        for(const p of (response.data as any)){
          this.messagesSizeAfter++
          this.users.push({
            "id": p.id,
            "name": p.name,
            "lastname": p.lastname,
            "username" : p.username,
          });
        }
        if (this.messagesSizeAfter > this.messagesSizeBefore){
          this.scrollToLastMessage()
        }
        this.messagesSizeBefore = this.messagesSizeAfter
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

  async getMessages(friendId: number) {
    this.selectedUser = friendId
    const body = {
      "senderId": localStorage.getItem('id'),
      "receiverId": friendId,
    }
    axios.post(environment.api + '/messages/getMessages', body)
      .then(response => {
        this.messagesSizeAfter = 0
        this.messages = []
        for(const p of (response.data as any)){
          this.messagesSizeAfter++
          this.messages.push({
            "senderId": p.senderId,
            "receiverId": p.receiverId,
            "text": p.text,
            "dateCreated" : p.dateCreated,
          });
        }
        
        if (this.firstTime) setTimeout(() => { this.scrollToLastMessage(); this.firstTime=false}, 300);
        if (this.messagesSizeAfter > this.messagesSizeBefore){
          setTimeout(() => { this.scrollToLastMessage();}, 300);
        }
          this.messagesSizeBefore = this.messagesSizeAfter
          console.log(this.messages)
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

  sendMessage() {
    if (this.messageText.trim() != ""){
    const body = {
      "senderId": localStorage.getItem('id'),
      "receiverId": this.selectedUser,
      "text": this.messageText
    }
    axios.post(environment.api + '/messages/sendMessage', body)
      .then(response => {
        this.getMessages(this.selectedUser)
        setTimeout(() => {
            this.scrollToLastMessage()
      }, 200);
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
      Swal.fire({
        icon: 'error',
        title: 'Your message is empty. Please, type something in.',
        showConfirmButton: false,
        timer: 1500
      })
    }
  }

  scrollToLastMessage() {
    var my_element = document.getElementById("lastMessage")?.scrollIntoView({
      behavior: "smooth",
      block: "start",
      inline: "nearest"
    });
  }

}
