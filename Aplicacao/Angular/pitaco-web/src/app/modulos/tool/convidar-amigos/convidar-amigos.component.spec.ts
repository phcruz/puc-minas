/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ConvidarAmigosComponent } from './convidar-amigos.component';

describe('ConvidarAmigosComponent', () => {
  let component: ConvidarAmigosComponent;
  let fixture: ComponentFixture<ConvidarAmigosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConvidarAmigosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConvidarAmigosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
