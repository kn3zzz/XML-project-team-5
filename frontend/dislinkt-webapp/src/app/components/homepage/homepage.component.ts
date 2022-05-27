import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PostsService } from 'src/app/services/posts.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  form: FormGroup;

  constructor(public fb: FormBuilder, public service: PostsService, private _snackBar: MatSnackBar) {
    this.form = this.fb.group({
      text: [''],
      img: [null],
    });
   }

  ngOnInit(): void {}
  uploadFile(event : any) {
    const file = (event.target as HTMLInputElement).files![0];
    this.form.patchValue({
      img: file,
    });
    this.form.get('img')!.updateValueAndValidity();
  }
  submitForm() {
    var formData: any = new FormData();
    formData.append('text', this.form.get('text')!.value);
    formData.append('file', this.form.get('img')!.value);

    this.service.createNewPost(formData).subscribe(
      (data) => {
        this.form = this.fb.group({
          text: [''],
          img: [null],
        });

        this._snackBar.open('Your post has been published.', 'Dissmiss', {
          duration: 3000
        });

        setTimeout(() => {
        }, 1000);
      },
      (error) => {
        this._snackBar.open('Creating post failed', 'Dissmiss', {
          duration: 3000
        });
      });;;
  }

}
