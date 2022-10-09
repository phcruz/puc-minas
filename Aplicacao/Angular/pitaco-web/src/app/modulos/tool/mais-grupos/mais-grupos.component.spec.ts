/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MaisGruposComponent } from './mais-grupos.component';

describe('MaisGruposComponent', () => {
  let component: MaisGruposComponent;
  let fixture: ComponentFixture<MaisGruposComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaisGruposComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaisGruposComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
