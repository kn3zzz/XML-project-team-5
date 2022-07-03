
export class CommentDTO{

    postId:number;
    userId:number;
    content:String;
    dateCreated:Date;
    constructor(){
        this.postId = 0;
        this.userId = 0;
        this.content = "";
        this.dateCreated = new Date()
    }
}