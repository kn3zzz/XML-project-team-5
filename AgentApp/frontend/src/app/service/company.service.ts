import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Company } from '../model/company';
import { JobOffer } from '../model/job-offer';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private _http: HttpClient) { }
  private readonly companyPath = 'http://localhost:9000/company';

  saveCompany(request: Company){
    return this._http.post<any>(`${this.companyPath}/save`, request);
  }

  getCompanyById(id: number){
    return this._http.get<Company>(`${this.companyPath}/`+ id)    
  }

  getAll(){
    return this._http.get<any>(`${this.companyPath}/`);
  }

  getAllJobOffers(){
    return this._http.get<JobOffer[]>(`${this.companyPath}/job_offer`)    
  }

  approveRequest(requestId: number){
    return this._http.put<any>(`${this.companyPath}/approve_request/`+ requestId, undefined)    
  }

  rejectRequest(requestId: number){
    return this._http.put<any>(`${this.companyPath}/reject_request/`+ requestId, undefined)    
  }

  saveJobOffer(jobOffer: JobOffer){
    return this._http.post<any>(`${this.companyPath}/` + jobOffer.companyId +  `/job_offer`, jobOffer)    
  }

  updateCompanyInfo(dto){
    return this._http.put<any>(`${this.companyPath}/updateCompanyInfo`, dto)    
  }
}
