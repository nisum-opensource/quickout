/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import 'rxjs/add/Observable/from';
import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterModule, RouterLinkWithHref} from '@angular/router';
import { RouterTestingModule} from '@angular/router/testing';
import { NavComponent } from './uslice.nav.component';
import { FlowService } from '../../../services/flow.service';



describe('NavComponent', () => {
  let component: NavComponent;
  let fixture: ComponentFixture<NavComponent>;
  let flowService: FlowService;
  let de:DebugElement;
  let el: HTMLElement;
  
  beforeEach(async(() => {
    
    TestBed.configureTestingModule({
      imports: [RouterModule, RouterTestingModule.withRoutes([])],
      declarations: [ NavComponent ],
      providers: [FlowService],
      schemas:[NO_ERRORS_SCHEMA],
      
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    flowService = TestBed.get(FlowService);
    de = fixture.debugElement;
    
   
   
  });
  it('should be created', () => {
     expect(component).toBeTruthy();
     });
  
  it('should have a defined component', () => {
    expect(component).toBeDefined();
  });
 
  // it('should render  logo', () => {
  //   const de = fixture.debugElement.query(By.css('img'));
  //   const el: HTMLImageElement = de.nativeElement;

  //   expect(el.currentSrc).toContain('');
  // });
  it('should have link of layout1 for   logo', () => {
  
    let debugElements = fixture.debugElement.queryAll(By.directive(RouterLinkWithHref));

    let index = debugElements.findIndex(de => de.properties['href'] === '/1')
    expect(index).toBeGreaterThan(-1);
  });

  it('Should have a routerlink to layout 1', () => {
    let debugElements = fixture.debugElement.queryAll(By.directive(RouterLinkWithHref));
     
    let index=debugElements.findIndex(de=>de.properties['href']=== '/1')
   expect(index).toBeGreaterThan(-1);
  });

  it('Should have a routerlink to layout 2', () => {
    let debugElements = fixture.debugElement.queryAll(By.directive(RouterLinkWithHref));

    let index = debugElements.findIndex(de => de.properties['href'] === '/2')
    expect(index).toBeGreaterThan(-1);
  });
  it('Should have a routerlink to layout 3', () => {
    let debugElements = fixture.debugElement.queryAll(By.directive(RouterLinkWithHref));

    let index = debugElements.findIndex(de => de.properties['href'] === '/3')
    expect(index).toBeGreaterThan(-1);
  });
  it('Should have a routerlink to layout 4', () => {
    let debugElements = fixture.debugElement.queryAll(By.directive(RouterLinkWithHref));

    let index = debugElements.findIndex(de => de.properties['href'] === '/seattle/')
    expect(index).toBeDefined;
  });
  it('should  call signin method', () => {
    component.ngOnInit();
    component.signin();
    fixture.nativeElement.querySelector('a').click();
    fixture.detectChanges();
    expect(component.signin).toBeDefined();
  });
  it('should call signout method', () => {
    component.ngOnInit();
    component.signout();
   fixture.nativeElement.querySelector('a').click();
   fixture.detectChanges();
    expect(component.signout).toBeDefined();
  });
  it('should   signin if loggedIn  is set to false', () => {
    component.isLoggedIn = false;
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      let containerElement = fixture.debugElement.query(By.css('.a'));
      expect(containerElement).toBeDefined();
    });
  });
  it('should signOut if loggedIn  is set to true', () => {
    component.isLoggedIn = true;
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      let containerElement = fixture.debugElement.query(By.css('.a'));
      expect(containerElement).toBeDefined();
    });
  });
});
