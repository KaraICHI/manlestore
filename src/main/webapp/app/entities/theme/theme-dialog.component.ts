import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Theme } from './theme.model';
import { ThemePopupService } from './theme-popup.service';
import { ThemeService } from './theme.service';

@Component({
    selector: 'jhi-theme-dialog',
    templateUrl: './theme-dialog.component.html'
})
export class ThemeDialogComponent implements OnInit {

    theme: Theme;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private themeService: ThemeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.theme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.themeService.update(this.theme));
        } else {
            this.subscribeToSaveResponse(
                this.themeService.create(this.theme));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Theme>>) {
        result.subscribe((res: HttpResponse<Theme>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Theme) {
        this.eventManager.broadcast({ name: 'themeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-theme-popup',
    template: ''
})
export class ThemePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private themePopupService: ThemePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.themePopupService
                    .open(ThemeDialogComponent as Component, params['id']);
            } else {
                this.themePopupService
                    .open(ThemeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
