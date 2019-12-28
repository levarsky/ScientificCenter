import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private baseUrl = "https://localhost:8769/sellers";
  constructor(private http: HttpClient) { }

  getPaymentTypes(token):Observable<any>{

    let param = new HttpParams();
    param = param.append('token',token);

    return this.http.get(this.baseUrl+"/pay/paymentTypes",{params:param});
  }

  getTokenRequest(token):Observable<any>{
    let param = new HttpParams();
    param = param.append('token',token);

    return this.http.get(this.baseUrl+"/pay/token",{params:param});
  }

  sendRequest(token,id):Observable<any>{
    let param = new HttpParams();
    param = param.append('token',token);
    param = param.append('id',id);

    return this.http.get(this.baseUrl+"/pay/paymentRequest",{params:param});
  }

}
