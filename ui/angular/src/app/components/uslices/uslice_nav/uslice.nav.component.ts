/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Component, OnInit, OnChanges} from '@angular/core';
import {FlowService} from '../../../services/flow.service';

@Component({
  selector: 'app-uslice-nav',
  templateUrl: './uslice.nav.component.html',
  styleUrls: ['./uslice.nav.component.css']
})
export class NavComponent implements OnInit {

  isLoggedIn = false;
  constructor(private flowService: FlowService) { }

  ngOnInit() {
    this.flowService.onClickAuthLink
      .subscribe ((isLoggedIn: boolean) => {
        this.isLoggedIn = isLoggedIn;
      })
  }

  signin() {
    this.flowService.onClickAuthLink.next(true);
    this.flowService.signIn();
  }

  signout() {
    this.flowService.onClickAuthLink.next(false);
    this.flowService.signOut();
  }
}
