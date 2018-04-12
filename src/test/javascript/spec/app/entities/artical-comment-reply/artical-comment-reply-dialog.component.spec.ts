/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentReplyDialogComponent } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply-dialog.component';
import { ArticalCommentReplyService } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.service';
import { ArticalCommentReply } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.model';
import { ArticalCommentService } from '../../../../../../main/webapp/app/entities/artical-comment';
import { ClientUserService } from '../../../../../../main/webapp/app/entities/client-user';

describe('Component Tests', () => {

    describe('ArticalCommentReply Management Dialog Component', () => {
        let comp: ArticalCommentReplyDialogComponent;
        let fixture: ComponentFixture<ArticalCommentReplyDialogComponent>;
        let service: ArticalCommentReplyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentReplyDialogComponent],
                providers: [
                    ArticalCommentService,
                    ClientUserService,
                    ArticalCommentReplyService
                ]
            })
            .overrideTemplate(ArticalCommentReplyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentReplyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentReplyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ArticalCommentReply(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.articalCommentReply = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'articalCommentReplyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ArticalCommentReply();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.articalCommentReply = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'articalCommentReplyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
