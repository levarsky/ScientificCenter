import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private baseUrl = "https://localhost:8088/registration";
  constructor(private http: HttpClient) { }

  signup(user):Observable<any>{
    return this.http.post(this.baseUrl+"/user",user)
  }

  register(client):Observable<any>{
    return this.http.post(this.baseUrl+"/client",client);
  }
}
