import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Payment} from "../model"
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

<<<<<<< HEAD
  private baseUrl = "http://localhost:8768/request/SKLJK";
=======
  private baseUrl = "https://localhost:8768/request";
>>>>>>> c05966b8498b08fd1eea735c655c498d0bff0424

  constructor(private http: HttpClient) { }

  pay(payment,id):Observable<any>{
    let param = new HttpParams();
    param = param.append('paymentId',id)
    return this.http.post(this.baseUrl+"/pay", payment,{params:param});
  }
}
