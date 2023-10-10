import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from '../shared/user.service';
import { AuthService } from '../shared/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService,
    private cookieService: CookieService
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = this.cookieService.get('token');
    if (token) {
      console.log('Token found');
      // Check if user is already logged in
      if (!this.authService.checkToken()) {
        console.log('User is already logged in');
        // Redirect user to home page
        this.router.navigate(['/login']);
        return false;
      } else {
        let role = next.data['permittedRole'] as string;
        if (role) {
          if (this.authService.roleMatch(role)) {
            console.log('rolematch');
          } else {
            console.log("role not permitted")
            this.router.navigate(['/login']);
            return false;
          }
        }
        return true;
      }
    } else {
      console.log('Token not found');
      this.router.navigate(['/login']);
      return false;
    }
  }

  checkLogin(url: string): boolean {
    if (this.authService.checkToken()) { return true; }
    this.router.navigate(['/login']);
    return false;
  }

}
