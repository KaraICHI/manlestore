import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClientUser } from './client-user.model';
import { ClientUserPopupService } from './client-user-popup.service';
import { ClientUserService } from './client-user.service';

@Component({
    selector: 'jhi-client-user-delete-dialog',
    templateUrl: './client-user-delete-dialog.component.html'
})
export class ClientUserDeleteDialogComponent {

    clientUser: ClientUser;

    constructor(
        private clientUserService: ClientUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientUserListModification',
                content: 'Deleted an clientUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-user-delete-popup',
    template: ''
})
export class ClientUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientUserPopupService: ClientUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clientUserPopupService
                .open(ClientUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
