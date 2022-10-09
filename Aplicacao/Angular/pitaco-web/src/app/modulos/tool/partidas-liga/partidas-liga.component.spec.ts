/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PartidasLigaComponent } from './partidas-liga.component';

describe('PartidasLigaComponent', () => {
  let component: PartidasLigaComponent;
  let fixture: ComponentFixture<PartidasLigaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PartidasLigaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PartidasLigaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
