import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private basicPath = 'https://localhost:8769';

  constructor(private http: HttpClient) { }

  pay(value: number): Observable<any> {
    console.log("service placam: " + value);
    return this.http.post<any>(this.basicPath + "/pay/"+ value, value);
  }
}
