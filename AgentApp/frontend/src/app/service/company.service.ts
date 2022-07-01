import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Company } from '../model/company';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private _http: HttpClient) { }
  private readonly companyPath = 'http://localhost:9000/company';

  saveCompany(request: Company){
    return this._http.post<any>(`${this.companyPath}/save`, request);
  }

  getAll(){
    return this._http.get<any>(`${this.companyPath}/`);
  }

  approveRequest(requestId: number){
    return this._http.put<any>(`${this.companyPath}/approve_request/`+ requestId, undefined)    
  }

  rejectRequest(requestId: number){
    return this._http.put<any>(`${this.companyPath}/reject_request/`+ requestId, undefined)    
  }

  updateCompanyInfo(dto){
    return this._http.put<any>(`${this.companyPath}/updateCompanyInfo`, dto)    
  }
}
