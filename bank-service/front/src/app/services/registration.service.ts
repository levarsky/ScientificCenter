import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private baseUrl = "https://localhost:8768/request";

  constructor(private http: HttpClient) { }

  pay(payment,id):Observable<any>{
    let param = new HttpParams();
    param = param.append('paymentId',id)
    return this.http.post(this.baseUrl+"/pay", payment,{params:param});
  }
}
