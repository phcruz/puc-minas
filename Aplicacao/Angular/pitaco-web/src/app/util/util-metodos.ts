
import * as cript from 'js-sha512';
import { Constants } from './constants';
import { stringify } from 'querystring';

export class UtilMetodos {

  public static criptografaSenha(senha: string): string {
    const senhaCriptografada = cript.sha512(senha);
    return senhaCriptografada.toLocaleUpperCase();
  }

  public static getItemSessionStorage(keyValue: string): string {
    let value = '';
    if (keyValue === Constants.AUTHORIZATION) {
      value = this.decriptaAuthorization(keyValue);
    } else {
      value = window.sessionStorage.getItem(keyValue);
    }
    return value;
  }

  public static setItemSessionStorage(keyValue: string, value: string) {
    if (keyValue === Constants.AUTHORIZATION) {
      this.encriptaAuthorization(keyValue, value);
    } else {
      window.sessionStorage.setItem(keyValue, value);
    }
  }

  public static removeItemSessionStorage(keyValue: string) {
    window.sessionStorage.removeItem(this.encodeBase64(keyValue));
  }

  public static clearSessionStorage() {
    return window.sessionStorage.clear();
  }

  public static getItemLocalStorage(keyValue: string): string {
    return window.localStorage.getItem(keyValue);
  }

  public static setItemLocalStorage(value: string, keyValue: string) {
    window.localStorage.setItem(value, keyValue);
  }

  public static removeItemLocalStorage(value: string) {
    window.localStorage.removeItem(value);
  }

  public static clearLocalStorage() {
    return window.localStorage.clear();
  }

  public static encodeBase64(value: string): string {
    return btoa(value);
  }

  public static decodeBase64(value: string): string {
    return atob(value);
  }

  public static getIdUsuarioTokenJWT(): string {
    const token = this.getItemSessionStorage(Constants.AUTHORIZATION);
    const claims = atob(token.split('.')[1]);
    const claimsObject = JSON.parse(claims);
    return claimsObject.user;
  }

  public static getNomeUsuarioTokenJWT(): string {
    const token = this.getItemSessionStorage(Constants.AUTHORIZATION);
    const claims = atob(token.split('.')[1]);
    const claimsObject = JSON.parse(claims);
    return claimsObject.name;
  }

  public static getEmailUsuarioTokenJWT(): string {
    const token = this.getItemSessionStorage(Constants.AUTHORIZATION);
    const claims = atob(token.split('.')[1]);
    const claimsObject = JSON.parse(claims);
    return claimsObject.sub;
  }

  public static getInfosUsuarioLogado(propert: string): string {
    const infosUser = JSON.parse(this.getItemSessionStorage(Constants.INFOS_USUARIO_LOGADO));
    if (infosUser !== null) {
      if (propert === Constants.PROPERT_NOME_USUARIO_LOGADO) {
        return infosUser.nome;
      }
      if (propert === Constants.PROPERT_EMAIL_USUARIO_LOGADO) {
        return infosUser.email;
      }
      if (propert === Constants.PROPERT_ID_USUARIO_LOGADO) {
        return infosUser.transaction.split('#')[1];
      }
    } else {
      return '';
    }
  }

  public static decriptaAuthorization(keyValue) {
    const value = window.sessionStorage.getItem(this.encodeBase64(keyValue));
    if (value) {
      return this.decodeBase64(window.sessionStorage.getItem(this.encodeBase64(keyValue)));
    }
    return value;
  }

  public static encriptaAuthorization(keyValue, value) {
    window.sessionStorage.setItem(this.encodeBase64(keyValue), this.encodeBase64(value));
  }

  public static validaSenha(senha: string): boolean {
    // ValidationExpression="(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,12})$"
    // ErrorMessage="Sua senha deve entre 6 e 16 caracteres. Use letras e números."
    const regex = new RegExp('(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$');

    return regex.test(senha);
  }

  public static createDateTimeString() {
    const today = new Date();
    const day = today.getDate() < 10 ? '0' + today.getDate() : today.getDate();
    const month = (today.getMonth() + 1) < 10 ? '0' + (today.getMonth() + 1) : (today.getMonth() + 1);
    const year = today.getFullYear();
    const hour = today.getHours() < 10 ? '0' + today.getHours() : today.getHours();
    const minutes = today.getMinutes() < 10 ? '0' + today.getMinutes() : today.getMinutes();

    return day + '/' + month + '/' + year + ' ' + hour + ':' + minutes;
  }

  public static convertDateTimeStringDate(dataTexto: string): Date {
    const dateArray = dataTexto.split(' ');
    const year = dateArray[0].split('/');
    const time = dateArray[1].split(':');

    return new Date(Number(year[2]), (Number(year[1]) - 1), Number(year[0]), Number(time[0]), Number(time[1]));
  }

  private static compareToDate(date1: Date, date2: Date) {
    // data1 < data2, retorna um valor menor que 0
    // data1 > data2, retorna um valor maior que 0
    // data1 = data2, então um 0 será mostrado no console
    if (date1.getTime() > date2.getTime()) {
      return 1;
    } else {
      if (date1.getTime() < date2.getTime()) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  public static comparaData(date1: Date, date2: Date): boolean {
    let verifica = false;
    const valor = this.compareToDate(date1, date2);

    if (valor < 0 || valor === 0) {
      verifica = true;
    } else {
      if (valor > 0) {
        verifica = false;
      }
    }
    return verifica;
  }
}
