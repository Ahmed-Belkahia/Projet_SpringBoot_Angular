import { TestBed } from '@angular/core/testing';

import { RelocationService } from './relocation.service';

describe('RelocationService', () => {
  let service: RelocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RelocationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
