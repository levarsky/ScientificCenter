import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private basicPath = 'center/user';
  private regPath = 'center/registration';

  constructor(private httpClient: HttpClient) { }

  signup(user):Observable<any>{
    return this.httpClient.post(this.regPath+"/user",user)
  }

  purchased() {
      return this.httpClient.get(this.basicPath + "/purchased") as Observable<any>;
  }

  subscriptions() {
      return this.httpClient.get(this.basicPath + "/subscriptions") as Observable<any>;
  }
}
