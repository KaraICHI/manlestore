import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Artical } from './artical.model';
import { ArticalPopupService } from './artical-popup.service';
import { ArticalService } from './artical.service';
import { ClientUser, ClientUserService } from '../client-user';

@Component({
    selector: 'jhi-artical-dialog',
    templateUrl: './artical-dialog.component.html'
})
export class ArticalDialogComponent implements OnInit {

    artical: Artical;
    isSaving: boolean;

    clientusers: ClientUser[];
    creatDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articalService: ArticalService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.artical.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articalService.update(this.artical));
        } else {
            this.subscribeToSaveResponse(
                this.articalService.create(this.artical));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Artical>>) {
        result.subscribe((res: HttpResponse<Artical>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Artical) {
        this.eventManager.broadcast({ name: 'articalListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-artical-popup',
    template: ''
})
export class ArticalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articalPopupService: ArticalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articalPopupService
                    .open(ArticalDialogComponent as Component, params['id']);
            } else {
                this.articalPopupService
                    .open(ArticalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
