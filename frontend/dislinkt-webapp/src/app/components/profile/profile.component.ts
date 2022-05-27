import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: any;
  id: number = 0;
  followBtn: any;
  followersNum: number = 0;
  followingNum: number = 0;
  public readonly updateFormGroup: FormGroup;
  public readonly experienceFormGroup: FormGroup;
  public readonly educationFormGroup: FormGroup;
  public readonly skillFormGroup: FormGroup;
  public readonly interestFormGroup: FormGroup;

  constructor(public readonly profileService: ProfileService,
              private readonly formBuilder: FormBuilder) { 
                this.updateFormGroup = this.formBuilder.group({
                  id: [],
                  email: ['', Validators.compose([Validators.required, Validators.email])],
                  name: [],
                  surname: [],
                  telephone: [],
                  gender: [],
                  birthdate: [],
                  biography: []
                });
                this.experienceFormGroup = this.formBuilder.group({
                  experience: Validators.required
                });
                this.educationFormGroup = this.formBuilder.group({
                  education: Validators.required
                });
                this.skillFormGroup = this.formBuilder.group({
                  skill: Validators.required
                });
                this.interestFormGroup = this.formBuilder.group({
                  interest: Validators.required
                });
              }

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {
    this.profileService.getUser().subscribe((res: any) => {
      console.log(res.user)
      this.user = res.user
    });
  }

  update() {
    if (this.updateFormGroup.invalid) {
      alert('Invalid input');
      return;
    }

    this.profileService.updateUser(this.updateFormGroup.getRawValue()).subscribe({
      next: (data) => {
      alert("Succesfully updated!")
      this.getUser();
    },
      error: (err) => {alert("Error has occured, user not updated!")}
    });
  }

  addNewExperience() {
    if (this.experienceFormGroup.invalid) {
      alert('Invalid input');
      return;
    }

    this.profileService.addNewExperience(this.experienceFormGroup.getRawValue()).subscribe({
      next: (data) => {
      alert("Succesfully added!")
      this.getUser();
    },
      error: (err) => {alert("Error has occured, new experience was not added!")}
    });
  }

  addNewEducation() {
    if (this.educationFormGroup.invalid) {
      alert('Invalid input');
      return;
    }

    this.profileService.addNewEducation(this.educationFormGroup.getRawValue()).subscribe({
      next: (data) => {
      alert("Succesfully added!")
      this.getUser();
    },
      error: (err) => {alert("Error has occured, new education was not added!")}
    });
  }

  addNewSkill() {
    if (this.skillFormGroup.invalid) {
      alert('Invalid input');
      return;
    }

    this.profileService.addNewSkill(this.skillFormGroup.getRawValue()).subscribe({
      next: (data) => {
      alert("Succesfully added!")
      this.getUser();
    },
      error: (err) => {alert("Error has occured, new skill was not added!")}
    });
  }

  addNewInterest() {
    if (this.interestFormGroup.invalid) {
      alert('Invalid input');
      return;
    }

    this.profileService.addNewInterest(this.interestFormGroup.getRawValue()).subscribe({
      next: (data) => {
      alert("Succesfully added!")
      this.getUser();
    },
      error: (err) => {alert("Error has occured, new interest was not added!")}
    });
  }

}
