/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AppToolComponent } from './app-tool.component';

describe('AppToolComponent', () => {
  let component: AppToolComponent;
  let fixture: ComponentFixture<AppToolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppToolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppToolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
