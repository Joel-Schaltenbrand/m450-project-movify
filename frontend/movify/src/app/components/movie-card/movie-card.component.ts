import { Component, OnInit } from '@angular/core';
import { ShuffleService } from 'src/app/services/movie/shuffle.service';
import { Movie } from 'src/app/models/movie.model';
import { ButtonService } from 'src/app/services/movie/button.service';
import { LikeService } from 'src/app/services/movie/like.service';

@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.scss'],
})
export class MovieCardComponent implements OnInit {
  movieData: Movie = {} as Movie;

  constructor(
    private buttonService: ButtonService,
    private likeService: LikeService
  ) {
    this.buttonService.buttonClicked.subscribe(() => {
      this.getRandomeMovie();
    });
    this.buttonService.likebuttonClicked.subscribe(() => {
      this.like();
    });
  }
  movieId: number = 0;

  ngOnInit() {
    this.getRandomeMovie();
  }

  getRandomeMovie() {
    let shuffleService = new ShuffleService();
    shuffleService
      .getMovie()
      .then((response) => {
        this.movieData = response;
        this.movieId = response.id;
      })
      .catch((error) => {
        console.error(error);
      });
  }

  like() {
    this.likeService.likeMovie(this.movieId);
    this.getRandomeMovie();
  }
}
