export class NotificationData{
    notificationsId : number;
    text : string;
    dateCreated: string;
    seen : boolean;

    constructor(){
        this.notificationsId = 1;
        this.text = '';
        this.dateCreated = '';
        this.seen = true;
    }
}