export class ConnectionDTO{
    id: number
    sender: number
    receiver: number
    connectionState: string

    constructor(){
        this.id = 0;
        this.sender = 0;
        this.receiver = 0;
        this.connectionState = "IDLE";
    }
}