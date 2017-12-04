/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { routes } from './app-routing.module';
import { Layout1Component } from './components/layouts/layout1/layout1.component';
import { Layout2Component } from './components/layouts/layout2/layout2.component';
import { Layout3Component } from './components/layouts/layout3/layout3.component';
import { GalleryComponent } from './components/layouts/gallery/gallery.component';
import { AppRoutingModule } from './app-routing.module';

describe('AppRoutingModule', () => {
    it('should contain route for (layout1)', () => {
        expect(routes).toContain({ path: '1', component: Layout1Component });
    });

    it('should contain route for (layout2)', () => {
        expect(routes).toContain({ path: '2', component: Layout2Component });
    });

    it('should contain route for (layout3)', () => {
        expect(routes).toContain({ path: '3', component: Layout3Component });
    });
    
});