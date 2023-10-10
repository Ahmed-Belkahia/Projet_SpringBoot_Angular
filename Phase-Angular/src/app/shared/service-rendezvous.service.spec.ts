import { TestBed } from '@angular/core/testing';

import { ServiceRendezvousService } from './service-rendezvous.service';

describe('ServiceRendezvousService', () => {
  let service: ServiceRendezvousService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceRendezvousService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
