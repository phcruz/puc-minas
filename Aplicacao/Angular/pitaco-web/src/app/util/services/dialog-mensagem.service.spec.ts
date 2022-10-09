/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DialogMensagemService } from './dialog-mensagem.service';

describe('Service: DialogMensagem', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DialogMensagemService]
    });
  });

  it('should ...', inject([DialogMensagemService], (service: DialogMensagemService) => {
    expect(service).toBeTruthy();
  }));
});
