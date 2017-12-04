/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { ModalComponent } from './uslice.modal.component';
import { FlowService } from '../../../services/flow.service';


describe('UsliceModalComponent', () => {
    let component: ModalComponent ;
    let fixture: ComponentFixture<ModalComponent >;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ModalComponent ],
            providers: [FlowService]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ModalComponent );
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should be created', () => {
        expect(component).toBeTruthy();
    });
    it('should correctly project modal title', () => {
        expect(fixture.debugElement.query(By.css('#myModal')).query(By.css('.modal-title')).nativeElement.innerHTML).toContain("Thank You!");
    });
    it('should correctly project modal body', () => {
        expect(fixture.debugElement.query(By.css('#myModal')).query(By.css('.modal-body')).nativeElement.innerHTML).toContain(" Order submitted successfully.");
    });
    it('should correctly project modal footer(close button)', () => {
        expect(fixture.debugElement.query(By.css('#myModal')).query(By.css('.modal-footer')).nativeElement.innerHTML).toContain("Close");
    });
});