import { CommentDTO } from "./comment.dto";

export class PostInfoDTO{   
    postId: number;
    username :string;
    userId: number;
    postText: string;
    imageString:string;
    comments : CommentDTO[];
    likedUsers: number[];
    dislikedUsers: number[];
    dateCreated:Date;
    
    constructor(){
        this.postId = 0;
        this.username = "";
        this.userId = 0 ; 
        this.postText = "";
        this.imageString = "";
        this.comments = [];
        this.likedUsers = [];
        this.dislikedUsers = [];
        this.dateCreated = new Date();
    }
}