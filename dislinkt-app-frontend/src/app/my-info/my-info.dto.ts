export class MyInfoData{
    name : string;
    lastname : string;
    username : string;
    email : string;
    phoneNumber : string;
    gender : string;
    birthDate : string;
    biography : string;
    workingExperience : string;
    education : string;
    skills : string;
    interests : string;
    privateProfile : boolean;
    notificationsOn : boolean;
    id: number

    constructor(){
        this.name = '';
        this.lastname = '';
        this.email = '';
        this.phoneNumber = '';
        this.username = '';
        this.gender = '';
        this.birthDate = '';
        this.biography = '';
        this.workingExperience = '';
        this.education = '';
        this.skills = '';
        this.interests = '';
        this.privateProfile = false;
        this.notificationsOn = true;
        this.id = 0;

    }
}