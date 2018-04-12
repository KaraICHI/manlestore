import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OrderComment } from './order-comment.model';
import { OrderCommentService } from './order-comment.service';

@Component({
    selector: 'jhi-order-comment-detail',
    templateUrl: './order-comment-detail.component.html'
})
export class OrderCommentDetailComponent implements OnInit, OnDestroy {

    orderComment: OrderComment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderCommentService: OrderCommentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderComments();
    }

    load(id) {
        this.orderCommentService.find(id)
            .subscribe((orderCommentResponse: HttpResponse<OrderComment>) => {
                this.orderComment = orderCommentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderComments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderCommentListModification',
            (response) => this.load(this.orderComment.id)
        );
    }
}
