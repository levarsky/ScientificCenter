import { Injectable } from '@angular/core';

import {Router} from "@angular/router";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {CookieService} from "ngx-cookie";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router,
              private http: HttpClient,
              private cookie:CookieService) { }

  private baseUrl = 'https://localhost:8088/';

  login(userLogin) {
    let params = new URLSearchParams();
    params.append('username',userLogin.username);
    params.append('password',userLogin.password);
    params.append('grant_type','password');

    const body = new HttpParams()
      .set('username', userLogin.username)
      .set('password', userLogin.password)
      .set('grant_type', 'password');

    let headers = new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Basic '+btoa("oauthorize:password")});


    let options = {headers:headers}

    return this.http.post(this.baseUrl+'uaa/oauth/token', body, options).subscribe(data=>{
      console.log(data);
      this.saveToken(data);

    },err=>{
      console.log(err);
    });

  }

  saveToken(token){
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    console.log(token.expires_in);
    console.log(token)
    this.cookie.putObject("sellers_token", token.access_token, {expires:expireDate.toString()});
    console.log('Obtained Access token');
    this.router.navigate(['home'])
  }

  getToken(){
    return this.cookie.get("sellers_token");
  }

  isValid():boolean{

    if(this.getToken()==null){
      return false;
    }
  }

  logout(){
    this.cookie.remove('sellers_token');
  }


}
