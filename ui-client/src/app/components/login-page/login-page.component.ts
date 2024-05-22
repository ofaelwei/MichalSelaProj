import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  username: string = "";
  password: string = "";

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  falseRedirect() {
    this.router.navigate(['/homepage']);
  }


}
