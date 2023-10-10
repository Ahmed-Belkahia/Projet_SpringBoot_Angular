import { TestBed } from '@angular/core/testing';

import { RentalofferserviceService } from './rentalofferservice.service';

describe('RentalofferserviceService', () => {
  let service: RentalofferserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RentalofferserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
