export class MessageDTO{
    senderId : number;
    receiverId : number;
    text : string;
    dateCreated: string;

    constructor(){
        this.senderId = 0;
        this.receiverId = 0;
        this.text = '';
        this.dateCreated = '';

    }
}