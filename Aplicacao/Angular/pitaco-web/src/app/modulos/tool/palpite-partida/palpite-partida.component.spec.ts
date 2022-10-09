/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PalpitePartidaComponent } from './palpite-partida.component';

describe('PalpitePartidaComponent', () => {
  let component: PalpitePartidaComponent;
  let fixture: ComponentFixture<PalpitePartidaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PalpitePartidaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PalpitePartidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
