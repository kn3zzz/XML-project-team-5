export class MessagesUserDTO{
    id: number
    name : string;
    lastname: string;
    username: string;

    constructor(){
        this.id = 0;
        this.name = '';
        this.lastname = '';
        this.username = '';
    }
}