import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterService } from 'src/app/services/authentication/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  constructor(
    private registerService: RegisterService,
    private router: Router
  ) { }
  

  ngOnInit(): void {
    let loggedIn = localStorage.getItem('login');
    if (loggedIn === 'true') {
      this.router.navigateByUrl('/home');
    }
  }

  register() {
    const emailInput = document.getElementById('email') as HTMLInputElement;
    const warning = document.getElementById("warning-password")
    const warningPassword = document.getElementById("warning-password-not-confirmed")
    const warningEmail = document.getElementById("warning-email")
    const passwordInput = document.getElementById(
      'password'
    ) as HTMLInputElement;
    const passwordConfirmInput = document.getElementById(
      'password-confirmation'
    ) as HTMLInputElement;

    const email = emailInput.value;
    const password = passwordInput.value;
    const passwordConfirmation = passwordConfirmInput.value;
    if ( warningEmail && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)){
      if (password == passwordConfirmation) {
        if (/[A-Z]/.test(password) && /[a-z]/.test(password) && /[0-9]/.test(password) && /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password) && password.length >= 8) {
          this.registerService.createAccount(email, password);
        } else {
          if (warning && warningPassword) {
            warning.style.display = "block"
            warningPassword.style.display = "none"
            warningEmail.style.display ="none"
          }
        }
      } else {
        if (warningPassword && warning) {
          warningPassword.style.display = "block"
          warning.style.display = "none"
          warningEmail.style.display ="none"
        }
      }
    } else if(warningEmail){
      warningEmail.style.display ="block"
    }
  }
}
