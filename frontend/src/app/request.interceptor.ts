import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, of, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    if(err.status === 403 || err.status === 401){
      sessionStorage.removeItem('token');
      this.router.navigateByUrl(`/login`);
      return of(err.message);
    }
    return throwError(err);
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let token = sessionStorage.getItem('token');
    if(token){
      request = request.clone({headers: request.headers.set('Authorization', `Bearer ${token}`)});
    }
    return next.handle(request).pipe(catchError(x => this.handleAuthError(x)));
  }
}
