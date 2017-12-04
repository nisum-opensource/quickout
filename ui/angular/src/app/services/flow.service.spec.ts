/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { TestBed, inject, fakeAsync, getTestBed, async, ComponentFixture } from '@angular/core/testing';
import { FlowService } from './flow.service';

describe('FlowService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FlowService]
    });
  });

  it('should be created', inject([FlowService], (service: FlowService) => {
    expect(service).toBeTruthy();
  }));
  it('can instantiate service when inject service',
    inject([FlowService], (service: FlowService) => {
    expect(service instanceof FlowService).toBe(true);
    }));

  it('should  call signIn method', inject([FlowService], (service: FlowService)=> {
    service.signIn();
    expect(service.signIn).toBeDefined();
  }));
  it('should  call signOut method', inject([FlowService], (service: FlowService) => {
    service.signOut();
    expect(service.signOut).toBeDefined();
  }));
  

});
