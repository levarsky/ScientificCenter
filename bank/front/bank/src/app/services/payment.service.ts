import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Payment} from "../model"
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private baseUrl = "https://localhost:8768/request";

  constructor(private http: HttpClient) { }

  pay(payment,id):Observable<any>{
    let param = new HttpParams();
    param = param.append('paymentId',id)
    return this.http.post(this.baseUrl+"/pay", payment,{params:param});
  }
}
