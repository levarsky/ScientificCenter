import { Injectable } from '@angular/core';
import {Observable, throwError} from "rxjs";
import {Router} from "@angular/router"
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private basicPath = 'center/pay/';

  constructor(private http: HttpClient, private router: Router) { }

  pay(value: number, ids : number[]):Observable<any>{
    return this.http.post(this.basicPath+value,ids);

  }

  subscribe(value: number, id :number):Observable<any>{
      return this.http.get(this.basicPath + "subscribe/"+value + "/" + id);

    }

  testRedirect():Observable<any>{
    return this.http.get('test/');
  }

}
