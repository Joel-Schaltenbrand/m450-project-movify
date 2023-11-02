import { Injectable } from '@angular/core';
import axios from 'axios';
import { USER_BASE_URL } from 'src/app/utils';
import { CookieService } from 'ngx-cookie-service';
import { Movie } from 'src/app/models/movie.model';
import { from } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FavouritesService {

  constructor(private cookieService: CookieService) {}

  async getFavourite(): Promise<Movie[]> {
    const formDate = new FormData();
    const cookieWithUserId = this.cookieService.get('LOGINID');
    formDate.append('id', cookieWithUserId);

    return new Promise(async (resolve, reject) => {
      try {
        const response = await axios.post(`${USER_BASE_URL}/getFavoriteMovies`, formDate ,{
          withCredentials: true,
        });
        resolve(response.data.favoriteMovies);
        
      } catch (error) {
        reject(error);
      }
    });
  }

  async removeMovie(id: number) {
    const formDate = new FormData();
    const cookieWithUserId = this.cookieService.get('LOGINID');

    formDate.append('id', cookieWithUserId);
    formDate.append('movieId', id.toString())

    axios
      .post(`${USER_BASE_URL}/removeFavorite`, formDate, { withCredentials: true })
      .then((response) => {

      })
      .catch((error) => {
        console.info(error);
      });
  }
}
