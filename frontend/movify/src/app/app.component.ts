import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { LogoutService } from './services/authentication/logout.service';
import { ButtonService } from './services/movie/button.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(
    private cookieService: CookieService,
    private logoutService: LogoutService,
    private buttonService: ButtonService
  ) {
    this.buttonService.buttonClicked.subscribe(() => {
      this.loading();
    });
    this.buttonService.likebuttonClicked.subscribe(() => {
      this.loading();
    });
    this.buttonService.loadingHomePage.subscribe(() => {
      this.loading();
    });
  }
  title = 'movify';
  isDarkThemeEnabled: boolean = false;
  isLoading: boolean = false;

  checkLogin() {
    let loggedIn = this.cookieService.check('LOGINID');
    return loggedIn;
  }

  ngOnInit(): void {
    this.loading();
    const html = document.querySelector('html') as HTMLElement;
    let theme = localStorage.getItem('theme');
    const overlay = document.querySelector('.overlay') as HTMLElement;

    if (theme == 'dark') {
      this.isDarkThemeEnabled = true;
      html.dataset['theme'] = 'dracula';
    } else {
      this.isDarkThemeEnabled = false;
      html.dataset['theme'] = 'winter';
      localStorage.setItem('theme', 'light');
    }
  }

  loading() {
    this.isLoading = true;
    setTimeout(() => {
      this.isLoading = false;
    }, 850);
  }

  switchTheme() {
    const html = document.querySelector('html') as HTMLElement;

    if (this.isDarkThemeEnabled) {
      html.dataset['theme'] = 'winter';
      localStorage.setItem('theme', 'light');
    } else {
      html.dataset['theme'] = 'dracula';
      localStorage.setItem('theme', 'dark');
    }

    this.handleOverlay();
  }

  handleOverlay() {
    let theme = localStorage.getItem('theme');
    const overlay = document.querySelector('.overlay') as HTMLElement;

    this.isDarkThemeEnabled = !this.isDarkThemeEnabled;

    switch (theme) {
      case 'dark': {
        overlay.style.background =
          'linear-gradient(180deg, #ffffff00, #ffffff00, #ffffff00, #ffffff00, #2a303c05, #2a303c)';
        break;
      }
      case 'light': {
        overlay.style.background =
          'linear-gradient(180deg, #ffffff00, #ffffff00, #ffffff00, #ffffff00, #ffffff05, #ffffff)';
        break;
      }
    }
  }

  getInitials() {
    let email = localStorage.getItem('email');
    let initials = email?.substring(0, 2);
    return initials?.toUpperCase();
  }

  logout() {
    this.logoutService.logoutUser();
  }
}
