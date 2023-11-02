import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { USER_BASE_URL } from '../../utils';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private router: Router, private cookieService: CookieService) {}


  loginUser(email: string, password: string): Promise<any> {
    const formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);


    return axios.post(`${USER_BASE_URL}/login`, formData, { withCredentials: true })
      .then((response) => {
        const expirationDate = new Date();
        expirationDate.setDate(expirationDate.getDate() + 1);
        this.cookieService.set('LOGINID', response.data.id, expirationDate);
        localStorage.setItem('email', email);
        this.router.navigateByUrl('/home');
        return response.data;

      })
      .catch((error)=>{
        console.log('Error: ' + error.response.status);
        throw new Error('Login failed');
      });
  }
}