import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/authentication/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
    let logedIn = localStorage.getItem("login")
    if (logedIn === "true") {
      this.router.navigateByUrl('/home')
    }
  }

  login() {
    let emailInput = document.getElementById('email') as HTMLInputElement;
    let passwordInput = document.getElementById('password') as HTMLInputElement;
    let warning = document.getElementById('warning') as HTMLInputElement;

    let email = emailInput.value;
    let password = passwordInput.value;

    this.loginService.loginUser(email, password)
    .then(response => {
    })
    .catch(error => {
      warning.style.display='block';
      // Handle the error here
     });
  }
}