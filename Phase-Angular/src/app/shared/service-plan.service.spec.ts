import { TestBed } from '@angular/core/testing';

import { ServicePlanService } from './service-plan.service';

describe('ServicePlanService', () => {
  let service: ServicePlanService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicePlanService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
