import { TestBed } from '@angular/core/testing';

import { ServicePostService } from './service-post.service';

describe('ServicePostService', () => {
  let service: ServicePostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicePostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
