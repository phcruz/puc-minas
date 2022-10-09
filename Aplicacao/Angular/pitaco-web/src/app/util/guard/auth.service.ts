import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UtilMetodos } from '../util-metodos';
import { Constants } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(private myRoute: Router) {}

  getToken() {
    let token = UtilMetodos.getItemSessionStorage(Constants.AUTHORIZATION);
    if (token !== null) {
      token = token.replace('Bearer ', '');
    }
    return token;
  }

  isAuthenticated() {
    return this.getToken() !== null;
  }

  logout() {
    localStorage.removeItem('LoggedInUser');
    this.myRoute.navigate(['Login']);
  }
}
