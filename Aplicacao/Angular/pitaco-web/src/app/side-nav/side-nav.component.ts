import { Component, OnInit, ViewEncapsulation, ViewChild, AfterViewInit } from '@angular/core';
import { MatTabChangeEvent, MatTabGroup } from '@angular/material';
import { Router } from '@angular/router';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SideNavComponent implements OnInit, AfterViewInit {

  selectedTabIndex = 0;

  @ViewChild('tabsPrincipal', { static: false }) tabsPrincipal: MatTabGroup;

  constructor(private router: Router,
              private indicatorService: IndicatorBehaviorService) { }

  ngOnInit() {
    this.ouvinteAlteraTabsViaDrawer();
  }

  getMudancaAbaTab(tabChangeEvent: MatTabChangeEvent) {
    this.selectedTabIndex = tabChangeEvent.index;
    if (this.selectedTabIndex === 0) {
      this.alteraCorDrawerSelecionado(0);
      this.router.navigate(['app/home']);
    }
    if (this.selectedTabIndex === 1) {
      this.alteraCorDrawerSelecionado(1);
      this.router.navigate(['app/ligas']);
    }
    if (this.selectedTabIndex === 2) {
      this.alteraCorDrawerSelecionado(2);
      this.router.navigate(['app/grupos']);
    }
    if (this.selectedTabIndex === 3) {
      this.alteraCorDrawerSelecionado(3);
      this.router.navigate(['app/ranking']);
    }
  }

  ngAfterViewInit(): void {
    if (window.location.href.includes('home')) {
      this.tabsPrincipal.selectedIndex = 0;
      this.alteraCorDrawerSelecionado(0);
    }
    if (window.location.href.includes('ligas')) {
      this.tabsPrincipal.selectedIndex = 1;
      this.alteraCorDrawerSelecionado(1);
    }
    if (window.location.href.includes('grupos')) {
      this.tabsPrincipal.selectedIndex = 2;
      this.alteraCorDrawerSelecionado(2);
    }
    if (window.location.href.includes('ranking')) {
      this.tabsPrincipal.selectedIndex = 3;
      this.alteraCorDrawerSelecionado(3);
    }
  }

  verificaTabsSelecionadaDrawer(value: number) {
    if (this.tabsPrincipal !== undefined && this.tabsPrincipal !== null) {
      this.tabsPrincipal.selectedIndex = value;
      this.alteraCorDrawerSelecionado(value);
    }
  }

  ouvinteAlteraTabsViaDrawer() {

    this.indicatorService.alteraTabsViaDrawer.subscribe(
      (quotes) => {
        if (quotes < 10) {
          this.verificaTabsSelecionadaDrawer(quotes);
        }
      }
    );
  }

  alteraCorDrawerSelecionado(value: number) {
    for (let i = 0; i < 7; i++) {
      if (i === value) {
        $('#list-item-' + i).addClass('claro');
      } else {
        $('#list-item-' + i).removeClass('claro');
      }
    }
  }
}
