import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { USER_BASE_URL } from '../../utils';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  constructor(private router: Router, private cookieService: CookieService) {}

  async createAccount(email: string, password: string) {
    const formDate = new FormData();
    formDate.append('email', email);
    formDate.append('password', password);


    axios
      .post(`${USER_BASE_URL}/register`, formDate, { withCredentials: true })
      .then((response) => {
        if (response.status != 201) {
          console.log('error');
        } else {
          const expirationDate = new Date();
          expirationDate.setDate(expirationDate.getDate() + 1);
          this.cookieService.set('LOGINID', response.data.id, expirationDate);
          localStorage.setItem('email', email);
          this.router.navigateByUrl('/home');
        }
      })
      .catch((error) => {
        console.info(error);
      });
  }
}
