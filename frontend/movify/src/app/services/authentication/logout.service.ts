import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { USER_BASE_URL } from '../../utils';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  constructor(private router: Router, private cookieService: CookieService) {}

  async logoutUser() {
    const formDate = new FormData();
    const cookieWithUserId = this.cookieService.get('LOGINID');

    formDate.append('id', cookieWithUserId);

    axios
      .post(`${USER_BASE_URL}/logout`, formDate, { withCredentials: true })
      .then((response) => {
        if (response.status != 200) {
          console.log('error: logout failed' + response.status);
        } else {
          this.cookieService.delete('LOGINID');
          this.router.navigateByUrl('/account/login');
        }
      })
      .catch((error) => {
        console.info(error);
      });
  }
}
