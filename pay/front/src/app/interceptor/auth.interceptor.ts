import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Router} from "@angular/router";

import {Observable} from "rxjs";
import {AuthService} from "../security/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor
{

  constructor(private _router: Router,private authService:AuthService) { }


  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {


    let token = this.authService.getToken();

    if (token){

      console.log("Token postoji")

      request = request.clone({headers: request.headers.set('Authorization', 'Bearer ' + JSON.parse(token))});

    }

    return next.handle(request);

  }
}
