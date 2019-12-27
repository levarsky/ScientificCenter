import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable, Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterClientService {

  private baseUrl = "http://localhost:8769/";
  constructor(private http: HttpClient) { }

  register(name : string):Observable<any>{
    return this.http.post(this.baseUrl + name, name);
  }

  getPaymentTypes():Observable<any>{
    return this.http.get(this.baseUrl+"paymentTypes");
  }
}
