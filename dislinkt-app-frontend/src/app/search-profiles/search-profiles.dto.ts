export class ProfilesData{
    username : string;
    name : string;
    lastname: string;
    biography : string;
    interests : string;
    id: number

    constructor(){
        this.name = '';
        this.lastname = '';
        this.username = '';
        this.biography = '';
        this.interests = '';
        this.id = 0;
    }
}