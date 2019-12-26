import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private basicPath = 'http://localhost:8080/pay/';

  constructor(private http: HttpClient) { }

  pay(value: number): Observable<any> {
    console.log("Service placam: " + value);
    return this.http.get<any>(this.basicPath+value);
  }
}
