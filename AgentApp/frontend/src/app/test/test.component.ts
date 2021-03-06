import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  sendRequest() {
    //const headers = { 'content-type': 'application/json'}  
    this.http.get('http://localhost:9000/api/test'/*, {'headers':headers}*/)
    .subscribe(data => { alert(data) })
  }
}
