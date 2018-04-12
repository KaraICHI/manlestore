import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OrderMaster } from './order-master.model';
import { OrderMasterService } from './order-master.service';

@Component({
    selector: 'jhi-order-master-detail',
    templateUrl: './order-master-detail.component.html'
})
export class OrderMasterDetailComponent implements OnInit, OnDestroy {

    orderMaster: OrderMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderMasterService: OrderMasterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderMasters();
    }

    load(id) {
        this.orderMasterService.find(id)
            .subscribe((orderMasterResponse: HttpResponse<OrderMaster>) => {
                this.orderMaster = orderMasterResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderMasters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderMasterListModification',
            (response) => this.load(this.orderMaster.id)
        );
    }
}
