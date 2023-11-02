import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Movie } from 'src/app/models/movie.model';
import { FavouritesService } from 'src/app/services/movie/favourites.service';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.scss'],
})
export class FavouritesComponent implements OnInit {
  constructor(
    private router: Router,
    private cookieService: CookieService,
    private favService: FavouritesService
  ) {}
  movieData: Movie[] = [];

  ngOnInit(): void {
    const hasLoginIdCookie: boolean = this.cookieService.check('LOGINID');

    if (hasLoginIdCookie != true) {
      this.router.navigate(['/account/login']);
    }

    this.favService.getFavourite().then((response) => {
      this.movieData = response;
    });
  }

  removeMovie(id: number) {
    this.favService.removeMovie(id);
    window.location.reload();
  }

  animateDelete(id: number) {
    const listItem = document.querySelector(`#item-${id}`) as HTMLElement;
    listItem.classList.add('anim-delete');

    setTimeout(() => {
      listItem.remove();
      this.removeMovie(id);
    }, 400);
  }
}
