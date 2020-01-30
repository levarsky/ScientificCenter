import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable, Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterClientService {

  private baseUrl = "https://localhost:8769/sellers";
  constructor(private http: HttpClient) { }

  signup(user):Observable<any>{
    return this.http.post(this.baseUrl+"/user",user)
  }

  register(client):Observable<any>{
    return this.http.post(this.baseUrl+"/client",client);
  }

  reg(name):Observable<any>{
    let param = new HttpParams();
    param = param.append('name',name);
    return this.http.post(this.baseUrl+"/client",{params:param});
  }


}
