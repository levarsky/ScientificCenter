import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable, Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransferServiceService {

  private baseUrl = "https://localhost:8083/transfer";
  constructor(private http: HttpClient) { }

  transfer(payment : any):Observable<any>{
    return this.http.post(this.baseUrl, payment);
  }
}
