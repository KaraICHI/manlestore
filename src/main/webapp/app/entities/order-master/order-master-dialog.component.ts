import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrderMaster } from './order-master.model';
import { OrderMasterPopupService } from './order-master-popup.service';
import { OrderMasterService } from './order-master.service';
import { Address, AddressService } from '../address';
import { ClientUser, ClientUserService } from '../client-user';

@Component({
    selector: 'jhi-order-master-dialog',
    templateUrl: './order-master-dialog.component.html'
})
export class OrderMasterDialogComponent implements OnInit {

    orderMaster: OrderMaster;
    isSaving: boolean;

    addresses: Address[];

    clientusers: ClientUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private orderMasterService: OrderMasterService,
        private addressService: AddressService,
        private clientUserService: ClientUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.addressService
            .query({filter: 'ordermaster-is-null'})
            .subscribe((res: HttpResponse<Address[]>) => {
                if (!this.orderMaster.addressId) {
                    this.addresses = res.body;
                } else {
                    this.addressService
                        .find(this.orderMaster.addressId)
                        .subscribe((subRes: HttpResponse<Address>) => {
                            this.addresses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clientUserService.query()
            .subscribe((res: HttpResponse<ClientUser[]>) => { this.clientusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orderMaster.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderMasterService.update(this.orderMaster));
        } else {
            this.subscribeToSaveResponse(
                this.orderMasterService.create(this.orderMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OrderMaster>>) {
        result.subscribe((res: HttpResponse<OrderMaster>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OrderMaster) {
        this.eventManager.broadcast({ name: 'orderMasterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAddressById(index: number, item: Address) {
        return item.id;
    }

    trackClientUserById(index: number, item: ClientUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-master-popup',
    template: ''
})
export class OrderMasterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderMasterPopupService: OrderMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderMasterPopupService
                    .open(OrderMasterDialogComponent as Component, params['id']);
            } else {
                this.orderMasterPopupService
                    .open(OrderMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
