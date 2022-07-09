export class JobOfferDTO{
    company : string;
    position : string;
    jobDescription: string;
    dailyActivities : string;
    preconditions : string;

    constructor(){
        this.company = '';
        this.position = '';
        this.jobDescription = '';
        this.dailyActivities = '';
        this.preconditions = '';
    }
}