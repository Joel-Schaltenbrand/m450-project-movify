import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { ButtonService } from 'src/app/services/movie/button.service';
import { LikeService } from 'src/app/services/movie/like.service';

@Component({
  selector: 'app-shuffle',
  templateUrl: './shuffle.component.html',
  styleUrls: ['./shuffle.component.scss'],
})
export class ShuffleComponent implements OnInit {
  constructor(
    private cdr: ChangeDetectorRef,
    private buttonService: ButtonService,
    private likeService: LikeService
  ) {}

  ngOnInit() {}

  onButtonCLick() {
    this.buttonService.emitButtonClick();
  }

  like() {
    this.buttonService.likeButtonCLick();
  }

  animateLike() {
    const card = document.querySelector('.card') as HTMLElement;

    card.classList.add('anim-like');

    setTimeout(() => {
      card.classList.remove('anim-like');
    }, 500);
  }

  animateDiscard() {
    const card = document.querySelector('.card') as HTMLElement;

    card.classList.add('anim-discard');

    setTimeout(() => {
      card.classList.remove('anim-discard');
    }, 500);
  }
}
