
export class CommentDTO{

    postId:number;
    username:String;
    userId:number;
    content:String;
    dateCreated:Date;
    constructor(){
        this.postId = 0;
        this.username="";
        this.userId = 0;
        this.content = "";
        this.dateCreated = new Date()
    }
}