import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Artical } from './artical.model';
import { ArticalService } from './artical.service';

@Component({
    selector: 'jhi-artical-detail',
    templateUrl: './artical-detail.component.html'
})
export class ArticalDetailComponent implements OnInit, OnDestroy {

    artical: Artical;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articalService: ArticalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticals();
    }

    load(id) {
        this.articalService.find(id)
            .subscribe((articalResponse: HttpResponse<Artical>) => {
                this.artical = articalResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articalListModification',
            (response) => this.load(this.artical.id)
        );
    }
}
