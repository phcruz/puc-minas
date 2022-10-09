/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DialogConfirmacaoService } from './dialog-confirmacao.service';

describe('Service: DialogConfirmacao', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DialogConfirmacaoService]
    });
  });

  it('should ...', inject([DialogConfirmacaoService], (service: DialogConfirmacaoService) => {
    expect(service).toBeTruthy();
  }));
});
