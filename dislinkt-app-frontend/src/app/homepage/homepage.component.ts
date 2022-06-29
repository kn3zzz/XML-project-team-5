import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor() {
   }

  ngOnInit(): void {
  }

  imgSrc = "";

  onselectFile(e:any) {
    if(e.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload=(event:any) => {
        this.imgSrc = event.target.result;
      }
    }
  }

  removeImgSrc() {
    this.imgSrc = "";
  }

}
