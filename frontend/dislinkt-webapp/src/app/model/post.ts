export interface Post {
    _id        : string,
	Username  : string,
	Text      : string,
	Likes     : number,
	Liked     : string[],
	Dislikes  : number,
	Disliked  : string[],
	CreatedOn : string,
	ImageUrl  : string,
	Comments  : Comment[]
}

export class Comment {
    Username! : string;
    Text !    : string;
}