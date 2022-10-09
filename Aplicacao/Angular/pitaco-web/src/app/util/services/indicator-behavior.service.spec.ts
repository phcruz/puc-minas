/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { IndicatorBehaviorService } from './indicator-behavior.service';

describe('Service: IndicatorBehavior', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IndicatorBehaviorService]
    });
  });

  it('should ...', inject([IndicatorBehaviorService], (service: IndicatorBehaviorService) => {
    expect(service).toBeTruthy();
  }));
});
