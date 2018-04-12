import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Artical } from './artical.model';
import { ArticalPopupService } from './artical-popup.service';
import { ArticalService } from './artical.service';

@Component({
    selector: 'jhi-artical-delete-dialog',
    templateUrl: './artical-delete-dialog.component.html'
})
export class ArticalDeleteDialogComponent {

    artical: Artical;

    constructor(
        private articalService: ArticalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articalService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articalListModification',
                content: 'Deleted an artical'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artical-delete-popup',
    template: ''
})
export class ArticalDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articalPopupService: ArticalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articalPopupService
                .open(ArticalDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
