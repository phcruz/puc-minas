/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { HeaderAuthorizationService } from './header-authorization.service';

describe('Service: HeaderAuthorization', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HeaderAuthorizationService]
    });
  });

  it('should ...', inject([HeaderAuthorizationService], (service: HeaderAuthorizationService) => {
    expect(service).toBeTruthy();
  }));
});
