export class CreatePostDTO{
    postId: number;
    userId: number;
    postText: string;
    imageString:string;
    dateCreated:Date;

    constructor(){
        this.postId = 0;
        this.userId = 0 ; 
        this.postText = "";
        this.imageString = "";
        this.dateCreated = new Date();

    }
}