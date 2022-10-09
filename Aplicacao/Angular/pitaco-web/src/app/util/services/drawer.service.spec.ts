/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DrawerService } from './drawer.service';

describe('Service: DrawerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DrawerService]
    });
  });

  it('should ...', inject([DrawerService], (service: DrawerService) => {
    expect(service).toBeTruthy();
  }));
});
