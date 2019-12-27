import { Injectable } from '@angular/core';
import {Observable, throwError} from "rxjs";
import {Router} from "@angular/router"
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private basicPath = 'api/pay/';

  constructor(private http: HttpClient, private router: Router) { }

  pay(value: number):Observable<any>{
    return this.http.get(this.basicPath+value);

  }
}
