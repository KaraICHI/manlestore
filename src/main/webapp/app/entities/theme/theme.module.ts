import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManleSharedModule } from '../../shared';
import {
    ThemeService,
    ThemePopupService,
    ThemeComponent,
    ThemeDetailComponent,
    ThemeDialogComponent,
    ThemePopupComponent,
    ThemeDeletePopupComponent,
    ThemeDeleteDialogComponent,
    themeRoute,
    themePopupRoute,
} from './';

const ENTITY_STATES = [
    ...themeRoute,
    ...themePopupRoute,
];

@NgModule({
    imports: [
        ManleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ThemeComponent,
        ThemeDetailComponent,
        ThemeDialogComponent,
        ThemeDeleteDialogComponent,
        ThemePopupComponent,
        ThemeDeletePopupComponent,
    ],
    entryComponents: [
        ThemeComponent,
        ThemeDialogComponent,
        ThemePopupComponent,
        ThemeDeleteDialogComponent,
        ThemeDeletePopupComponent,
    ],
    providers: [
        ThemeService,
        ThemePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManleThemeModule {}
