import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ButtonService {
  buttonClicked = new EventEmitter<void>();
  likebuttonClicked = new EventEmitter<void>();
  loadingHomePage = new EventEmitter<void>()

  emitButtonClick() {
    this.buttonClicked.emit();
  }

  likeButtonCLick(){
    this.likebuttonClicked.emit();
  }

  homeLoading(){
    this.loadingHomePage.emit();
  }
}
