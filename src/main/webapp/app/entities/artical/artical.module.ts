import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    ArticalService,
    ArticalPopupService,
    ArticalComponent,
    ArticalDetailComponent,
    ArticalDialogComponent,
    ArticalPopupComponent,
    ArticalDeletePopupComponent,
    ArticalDeleteDialogComponent,
    articalRoute,
    articalPopupRoute,
} from './';

const ENTITY_STATES = [
    ...articalRoute,
    ...articalPopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticalComponent,
        ArticalDetailComponent,
        ArticalDialogComponent,
        ArticalDeleteDialogComponent,
        ArticalPopupComponent,
        ArticalDeletePopupComponent,
    ],
    entryComponents: [
        ArticalComponent,
        ArticalDialogComponent,
        ArticalPopupComponent,
        ArticalDeleteDialogComponent,
        ArticalDeletePopupComponent,
    ],
    providers: [
        ArticalService,
        ArticalPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleArticalModule {}
