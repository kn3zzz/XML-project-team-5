import { Company } from "./company";
import { Position } from "./position";

export class JobOffer {
    id: number;
    companyId: number;
    position: Position = new Position;
    jobDescription!: string;
    dailyActivities!: string;
    company: Company = new Company;
}