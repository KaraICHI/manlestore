import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArticalComment } from './artical-comment.model';
import { ArticalCommentPopupService } from './artical-comment-popup.service';
import { ArticalCommentService } from './artical-comment.service';

@Component({
    selector: 'jhi-artical-comment-delete-dialog',
    templateUrl: './artical-comment-delete-dialog.component.html'
})
export class ArticalCommentDeleteDialogComponent {

    articalComment: ArticalComment;

    constructor(
        private articalCommentService: ArticalCommentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articalCommentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articalCommentListModification',
                content: 'Deleted an articalComment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artical-comment-delete-popup',
    template: ''
})
export class ArticalCommentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articalCommentPopupService: ArticalCommentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articalCommentPopupService
                .open(ArticalCommentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
