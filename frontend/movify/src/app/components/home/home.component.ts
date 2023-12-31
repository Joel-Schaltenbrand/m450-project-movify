import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ButtonService } from 'src/app/services/movie/button.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(
    private router: Router,
    private cookieService: CookieService,
    private butttonService: ButtonService
  ) {}

  ngOnInit(): void {
    const hasLoginIdCookie: boolean = this.cookieService.check('LOGINID');

    if (hasLoginIdCookie != true) {
      this.router.navigate(['/account/login']);
    }

    this.butttonService.homeLoading();
  }
}
