import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClientUser } from './client-user.model';
import { ClientUserPopupService } from './client-user-popup.service';
import { ClientUserService } from './client-user.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-client-user-dialog',
    templateUrl: './client-user-dialog.component.html'
})
export class ClientUserDialogComponent implements OnInit {

    clientUser: ClientUser;
    isSaving: boolean;

    products: Product[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private clientUserService: ClientUserService,
        private productService: ProductService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productService.query()
            .subscribe((res: HttpResponse<Product[]>) => { this.products = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clientUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientUserService.update(this.clientUser));
        } else {
            this.subscribeToSaveResponse(
                this.clientUserService.create(this.clientUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ClientUser>>) {
        result.subscribe((res: HttpResponse<ClientUser>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ClientUser) {
        this.eventManager.broadcast({ name: 'clientUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-client-user-popup',
    template: ''
})
export class ClientUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientUserPopupService: ClientUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clientUserPopupService
                    .open(ClientUserDialogComponent as Component, params['id']);
            } else {
                this.clientUserPopupService
                    .open(ClientUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
