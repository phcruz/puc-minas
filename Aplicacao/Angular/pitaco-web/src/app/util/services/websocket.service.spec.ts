/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { WebSocketService } from './websocket.service';

describe('Service: App', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WebSocketService]
    });
  });

  it('should ...', inject([WebSocketService], (service: WebSocketService) => {
    expect(service).toBeTruthy();
  }));
});
