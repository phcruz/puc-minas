/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PreviousRouteServiceService } from './previous-route-service.service';

describe('Service: PreviousRouteService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PreviousRouteServiceService]
    });
  });

  it('should ...', inject([PreviousRouteServiceService], (service: PreviousRouteServiceService) => {
    expect(service).toBeTruthy();
  }));
});
