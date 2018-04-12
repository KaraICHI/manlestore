import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrderMaster } from './order-master.model';
import { OrderMasterPopupService } from './order-master-popup.service';
import { OrderMasterService } from './order-master.service';

@Component({
    selector: 'jhi-order-master-delete-dialog',
    templateUrl: './order-master-delete-dialog.component.html'
})
export class OrderMasterDeleteDialogComponent {

    orderMaster: OrderMaster;

    constructor(
        private orderMasterService: OrderMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderMasterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderMasterListModification',
                content: 'Deleted an orderMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-master-delete-popup',
    template: ''
})
export class OrderMasterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderMasterPopupService: OrderMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderMasterPopupService
                .open(OrderMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
