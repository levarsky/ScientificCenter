import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private baseUrl = "https://localhost:8088/paypal/client";

  constructor(private http: HttpClient) { }

  checkUrl(id):Observable<any>{
    let param = new HttpParams();
    param = param.append('tokenId',id)
    return this.http.get(this.baseUrl+"/checkUrl",{params:param});
  }

  register(client,id):Observable<any>{
    let param = new HttpParams();
    param = param.append('tokenId',id)
    return this.http.post(this.baseUrl, client,{params:param});
  }
}
