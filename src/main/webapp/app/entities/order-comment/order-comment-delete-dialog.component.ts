import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrderComment } from './order-comment.model';
import { OrderCommentPopupService } from './order-comment-popup.service';
import { OrderCommentService } from './order-comment.service';

@Component({
    selector: 'jhi-order-comment-delete-dialog',
    templateUrl: './order-comment-delete-dialog.component.html'
})
export class OrderCommentDeleteDialogComponent {

    orderComment: OrderComment;

    constructor(
        private orderCommentService: OrderCommentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderCommentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderCommentListModification',
                content: 'Deleted an orderComment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-comment-delete-popup',
    template: ''
})
export class OrderCommentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderCommentPopupService: OrderCommentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderCommentPopupService
                .open(OrderCommentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
