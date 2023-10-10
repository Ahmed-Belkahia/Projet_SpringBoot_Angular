import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { User } from '../modele/user/user';
import { UserService } from './user.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl_auth = 'http://localhost:8000/PI/auth/authenticate';
  private baseurl_register = 'http://localhost:8000/PI/auth/register/withoutimage';
  private sendEmail_Forgot = 'http://localhost:8000/PI/auth/sendMail';
  private update_Password:"http://localhost:8000/PI/user/UpdatePassword?newPassword='+newPassword+'&oldPassword='+oldPassword";
  private forgot_password:"http://localhost:8000/PI/auth/forgotPassword";

  constructor(private http: HttpClient, private cookieService: CookieService,private userService:UserService) { }


  roleMatch(role: string): boolean {
    var isMatch = false;
    var token = this.cookieService.get('token');
    if (token != null && token.length!=0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userRoles = payLoad.roles as string[];
      //console.log(payLoad.role);
      isMatch = userRoles.includes(role);
    }

    return isMatch;
  }

  register(User: User): Observable<any> {
    return this.http.post<User>(`${this.baseurl_register}` ,User)
  }

  login(email: string, password: string): Observable<any> {
    const body = { email: email, password: password };
    return this.http.post<any>(`${this.baseUrl_auth}`, body);
  }

  public getUserIdFromToken(): string {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.userid as string;
      return userName
    }
    return "";
  }

  public getTaxIdFromToken(): number {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.taxuser as string;
      return parseInt(userName, 10);
        }
    return 0;
  }
  public getUserIdFromToken1(): number {
    var token = this.cookieService.get("token");
    if (token != null && token.length != 0) {
      var payLoad = JSON.parse(window.atob(token.split('.')[1]));
      var userName = payLoad.userid as string;
      return parseInt(userName, 10);
    }
    return 1;
  }
  

  checkToken(): boolean {
    const token = this.cookieService.get('token');
    if (token) {
      // Le token existe dans le cookie
      return true;
    } else {
      // Le token n'existe pas dans le cookie
      return false;
    }
  }
  getToken(): string {
    return this.cookieService.get('token');
  }
  getAuthToken(): string {
    return this.cookieService.get('token');
}
  sendEmailForgot(email: string){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    const body = {
      email: email
    };
    return this.http.post<any>(`${this.sendEmail_Forgot}`, body,httpOptions);
  }
  updatePassword(oldPassword: string, newPassword: string) {
    const authToken = this.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = {  headers: headers };// créez un objet contenant les paramètres à envoyer
   const updatePass = 'http://localhost:8000/PI/user/UpdatePassword?newPassword='+newPassword+'&oldPassword='+oldPassword;
    return this.http.put( updatePass , {oldPassword,newPassword} ,  options )// envoyez la requête HTTP et convertissez la réponse en Promise
  }
  forgotPassword( password: string){
    const authToken = this.getAuthToken();
    const headers = {
        'Authorization': `Bearer ${authToken}`,
        'Content-Type': 'application/json'
    };
    const options = {  headers: headers };// créez un objet contenant les paramètres à envoyer
   const forgetPass = 'http://localhost:8000/PI/auth/forgotPassword?password='+password;
   return this.http.put( forgetPass , password,  options )
  }
}
