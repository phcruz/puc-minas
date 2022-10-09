import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  interval;

  constructor() { }

  ngOnInit() {
    this.startTimer(10);
  }

  fadeinFadeOut() {
    (function() {
      let cnt = 0;
      const divObito = $('#div-img-404');
      function next() {
          if (cnt < 5) {
            divObito.fadeTo(2000, .1).fadeTo(2000, 1, next);
            divObito.animate({height: '0px', width: '0px'});
            ++cnt;
          }
      }
      next();
    })();
  }

  startTimer(seconds) {
    let counter = seconds;
    this.interval = setInterval(() => {
      counter--;
      if (counter === 1) {
        $('#card-img-404').addClass('rotate-404');
      }
      if (counter === 0) {
        $('#card-img-404').removeClass('rotate-404');
        clearInterval(this.interval);
        this.startTimer(seconds);
      }
    }, 1000);
  }
}
