/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { IndicarLigaComponent } from './indicar-liga.component';

describe('IndicarLigaComponent', () => {
  let component: IndicarLigaComponent;
  let fixture: ComponentFixture<IndicarLigaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndicarLigaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicarLigaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
