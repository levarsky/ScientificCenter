import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  private basicPath = 'http://localhost:8080';

  constructor() { }

  pay(value: number){
    console.log(value);
  }
}
