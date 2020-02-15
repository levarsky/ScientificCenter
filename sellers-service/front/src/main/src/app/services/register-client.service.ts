import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable, Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterClientService {

  private baseUrl = "https://localhost:8088/sellers";
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

  registerPayment(serviceName,mode):Observable<any>{
    let param = new HttpParams();
    param = param.append('serviceName',serviceName);
    param = param.append('mode',mode);

    return this.http.get(this.baseUrl+"/client/registerPayment",{params:param});
  }

  getClientPayments():Observable<any>{
    return this.http.get(this.baseUrl+"/pay/clientPaymentTypes");
  }

  getAllPayments():Observable<any>{
    return this.http.get(this.baseUrl+"/pay/allPaymentTypes");
  }


}
