import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private basicPath = 'https://localhost:8769/sellers/red';

  constructor(private http: HttpClient) { }

  pay(value: number): Observable<any> {
    console.log("Service placam: " + value);
    return this.http.post<any>(this.basicPath + value, value);
  }
}
