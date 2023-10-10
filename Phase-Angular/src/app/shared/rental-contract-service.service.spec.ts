import { TestBed } from '@angular/core/testing';

import { RentalContractServiceService } from './rental-contract-service.service';

describe('RentalContractServiceService', () => {
  let service: RentalContractServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RentalContractServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
