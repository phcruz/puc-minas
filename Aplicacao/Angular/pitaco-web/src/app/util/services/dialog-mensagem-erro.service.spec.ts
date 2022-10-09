/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DialogMensagemErroService } from './dialog-mensagem-erro.service';

describe('Service: DialogMensagemErro', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DialogMensagemErroService]
    });
  });

  it('should ...', inject([DialogMensagemErroService], (service: DialogMensagemErroService) => {
    expect(service).toBeTruthy();
  }));
});
