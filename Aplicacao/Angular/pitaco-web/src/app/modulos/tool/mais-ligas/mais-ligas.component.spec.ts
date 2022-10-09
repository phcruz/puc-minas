/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MaisLigasComponent } from './mais-ligas.component';

describe('MaisLigasComponent', () => {
  let component: MaisLigasComponent;
  let fixture: ComponentFixture<MaisLigasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaisLigasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaisLigasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
