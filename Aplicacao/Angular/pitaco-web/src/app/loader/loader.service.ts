import { Injectable } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';


@Injectable({
  providedIn: 'root'
})
export class LoaderService {

  constructor(public loader: NgxUiLoaderService) { }

  public showLoader() {
    this.loader.start();
  }

  public hideLoader() {
    this.loader.stop();
  }

  public showLoaderTimeout() {
    this.showLoader();
    setTimeout(() => {
      this.hideLoader();
    }, 100);
  }

}
