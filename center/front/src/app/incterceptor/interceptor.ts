// import {Injectable} from "@angular/core";
// import {
//   HttpEvent,
//   HttpHandler,
//   HttpRequest,
//   HttpClient,
//   HttpHeaders,
//   HttpResponse,
//   HttpErrorResponse
// } from "@angular/common/http";
// import {HttpInterceptor} from "@angular/common/http";
// import { Observable } from "rxjs";
// import {Router} from "@angular/router";
// import {catchError, map} from "rxjs/operators";
//
//
// @Injectable()
// export class Interceptor implements HttpInterceptor
// {
//
//   constructor(private _router: Router) { }
//
//
//   intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
//   {
//     //
//
//     // @ts-ignore
//     return next.handle(request).pipe(
//       map((event: HttpEvent<any>) => {
//         if (event instanceof HttpErrorResponse) {
//
//
//         }
//         return event;
//       }
//
//       ));
//
//
//     //request = request.clone({headers: request.headers.set('Authorization', 'Bearer ' + token)});
//
//
//
//
//   }
// }
