export class UserDTO{

    name: String;
    lastname: String;
    username: String;
    email: String;
    phoneNumber: String;
    gender: String;
    birthDate: String;
    biography: String;
    workingExperience: String;
    education: String;
    skills: String;
    interests: String;
    privateProfile: boolean;
    notificationsOn : boolean;
    id:number;
    constructor(){
    this.name = ""
    this.lastname = ""
    this.username = ""
    this.email = ""
    this.phoneNumber = ""
    this.gender = ""
    this.birthDate = ""
    this.biography = ""
    this.workingExperience = ""
    this.education = ""
    this.skills = ""
    this.interests = ""
    this.privateProfile = false;
    this.notificationsOn = true;
    this.id = 0;
    }
}