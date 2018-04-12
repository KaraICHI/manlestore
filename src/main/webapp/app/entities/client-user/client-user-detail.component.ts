import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ClientUser } from './client-user.model';
import { ClientUserService } from './client-user.service';

@Component({
    selector: 'jhi-client-user-detail',
    templateUrl: './client-user-detail.component.html'
})
export class ClientUserDetailComponent implements OnInit, OnDestroy {

    clientUser: ClientUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clientUserService: ClientUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClientUsers();
    }

    load(id) {
        this.clientUserService.find(id)
            .subscribe((clientUserResponse: HttpResponse<ClientUser>) => {
                this.clientUser = clientUserResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClientUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clientUserListModification',
            (response) => this.load(this.clientUser.id)
        );
    }
}
