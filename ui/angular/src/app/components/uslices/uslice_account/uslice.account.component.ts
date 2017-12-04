/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Component, Input} from '@angular/core';
import {Step} from '../../../models/model';

@Component({
    moduleId: module.id,
    selector: 'app-uslice-account-info',
    templateUrl: './uslice.account.component.html',
    styleUrls: ['./uslice.account.component.css']
})

export class AccountInformationComponent {
    @Input()step: Step;
   
}
