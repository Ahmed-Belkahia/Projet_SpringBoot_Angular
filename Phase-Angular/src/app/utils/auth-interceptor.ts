import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { EMPTY, Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie-service";
import { ConfirmregisterComponent } from "../front/userfront/confirmregister/confirmregister.component";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private cookieService: CookieService, private confirmRegisterComponent: ConfirmregisterComponent) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.cookieService.get('token') != null) {
      const clonedReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + this.cookieService.get('token'))
      });

      if (req.url.indexOf('/confirmRegister') > -1) {
        // Handle /confirmRegister requests
        if (this.confirmRegisterComponent.confirmCode()) {
          this.router.navigateByUrl('/forgotpwd');
          return next.handle(req);
        } else {
          // Code is invalid, delete token and redirect to login page
          this.cookieService.delete('token');
          this.router.navigateByUrl('/login');
          return EMPTY;
        }
      } else {
        return next.handle(clonedReq).pipe(
          tap(
            succ => { },
            err => {
              if (err.status === 0) {
                this.cookieService.delete('token');
                this.router.navigateByUrl('/login');
              } else if (err.status === 403) {
                this.cookieService.delete('token');
                this.router.navigateByUrl('/login');
              } else if (err.status === 401) {
                this.cookieService.delete('token');
                this.router.navigateByUrl('/login');
              }
            }
          )
        )
      }
    } else {
      return next.handle(req.clone());
    }
  }
}
