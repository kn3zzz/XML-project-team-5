export class ConnectionDTO{
    id: number
    sender: number
    receiver: number
    state: string

    constructor(){
        this.id = 0;
        this.sender = 0;
        this.receiver = 0;
        this.state = "IDLE";
    }
}