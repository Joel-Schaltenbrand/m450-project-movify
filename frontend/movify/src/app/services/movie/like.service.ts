import { Injectable } from '@angular/core';
import axios from 'axios';
import { USER_BASE_URL } from 'src/app/utils';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class LikeService {
  constructor(private cookieService: CookieService) {}

  async likeMovie(movieId: number) {
    const formDate = new FormData();
    const cookieWithUserId = this.cookieService.get('LOGINID');
    formDate.append('id', cookieWithUserId);
    formDate.append('movieId', movieId.toString())

    axios
      .post(`${USER_BASE_URL}/addFavorite`, formDate, { withCredentials: true })
      .then((response) => {
        
      })
      .catch((error) => {
        console.info(error);
      });
  }
}
