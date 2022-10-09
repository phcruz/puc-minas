import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { Router} from '@angular/router';
import { Observable } from 'rxjs';
import decode from 'jwt-decode';
import { UtilMetodos } from '../util-metodos';
import { ToastService } from '../services/toast.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService,
              private myRoute: Router,
              private toastService: ToastService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.auth.isAuthenticated()) {
      const tokenPayload = decode(this.auth.getToken());

      const dataExpiracao = new Date(tokenPayload.exp * 1000);
      const dataAtual = new Date();

      if (UtilMetodos.comparaData(dataAtual, dataExpiracao)) {
        return true;
      }

      this.myRoute.navigate(['login']);
      this.exibeToast();
      return false;
    } else {
      this.myRoute.navigate(['login']);
      this.exibeToast();
      return false;
    }
  }

  exibeToast() {
    this.toastService.showToast('Sua sessão expirou. Faça login novamente. 😉');
  }
}
