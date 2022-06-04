import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { HomepageService } from '../services/homepage.service';
import { PostDto } from './postDTO';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  posts: PostDto[]
  constructor(private homepageService: HomepageService ) {
    this.posts = []
   }

  ngOnInit(): void {
    this.getPosts();
  }

  getPosts() {
    this.homepageService.getPosts().subscribe((data: any) => {
     for (const d of (data as any)) {
       this.posts.push({
         "id": d.id,
         "content": d.content,
       });
     }
     console.log(this.posts)
   });    
 }
}
