import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Theme } from './theme.model';
import { ThemeService } from './theme.service';

@Component({
    selector: 'jhi-theme-detail',
    templateUrl: './theme-detail.component.html'
})
export class ThemeDetailComponent implements OnInit, OnDestroy {

    theme: Theme;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private themeService: ThemeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInThemes();
    }

    load(id) {
        this.themeService.find(id)
            .subscribe((themeResponse: HttpResponse<Theme>) => {
                this.theme = themeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInThemes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'themeListModification',
            (response) => this.load(this.theme.id)
        );
    }
}
