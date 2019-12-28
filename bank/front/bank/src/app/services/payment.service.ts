import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import {Payment} from "../model"
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private baseUrl = "http://localhost:8768/request";

  constructor(private http: HttpClient) { }

  pay(payment : Payment):Observable<any>{
    return this.http.post(this.baseUrl, payment);
  }
}
