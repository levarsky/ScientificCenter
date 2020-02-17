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

  private baseUrl = 'https://localhost:8080/';

  login(userLogin) {
    let params = new URLSearchParams();
    params.append('username',userLogin.username);
    params.append('password',userLogin.password);
    params.append('grant_type','password');

    const body = new HttpParams()
      .set('username', userLogin.username)
      .set('password', userLogin.password)
      .set('grant_type', 'password');

    let headers = new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Basic '+btoa("scienceCenter:clientPassword")});


    let options = {headers:headers}

    return this.http.post(this.baseUrl+'center/oauth/token', body, options).subscribe(data=>{
      console.log(data);
      this.saveToken(data);
      //window.location.reload();
    },err=>{
      console.log(err);
    });

  }

  saveToken(token){
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    console.log(token.expires_in);
    console.log(token)
    this.cookie.putObject("access_token", token.access_token, {expires:expireDate.toString()});
    console.log('Obtained Access token');
    //this.router.reload(['home'])
    window.location.href="/"
  }

  getToken(){
    return this.cookie.get("access_token");
  }

  isValid():boolean{

    if(this.getToken()==null){
      return false;
    }
  }

  isLoggedIn(){
    if(this.cookie.getObject("access_token") == null)
      return false;

    return true;
  }

  logout(){
    this.cookie.remove('access_token');
    window.location.href="/"
  }
}
