import { TestBed } from '@angular/core/testing';

import { ServiceOffreService } from './service-offre.service';

describe('ServiceoffreService', () => {
  let service: ServiceOffreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceOffreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
