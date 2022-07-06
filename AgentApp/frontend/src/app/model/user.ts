import { Company } from "./company";

export class User {
    id: number;
    email: string = "";
	password: string;  
	userType: string;
    company: Company;
}
