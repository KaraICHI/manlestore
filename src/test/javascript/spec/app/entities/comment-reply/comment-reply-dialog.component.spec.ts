/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManleTestModule } from '../../../test.module';
import { CommentReplyDialogComponent } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply-dialog.component';
import { CommentReplyService } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.service';
import { CommentReply } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.model';
import { OrderCommentService } from '../../../../../../main/webapp/app/entities/order-comment';
import { ClientUserService } from '../../../../../../main/webapp/app/entities/client-user';

describe('Component Tests', () => {

    describe('CommentReply Management Dialog Component', () => {
        let comp: CommentReplyDialogComponent;
        let fixture: ComponentFixture<CommentReplyDialogComponent>;
        let service: CommentReplyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [CommentReplyDialogComponent],
                providers: [
                    OrderCommentService,
                    ClientUserService,
                    CommentReplyService
                ]
            })
            .overrideTemplate(CommentReplyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentReplyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentReplyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CommentReply(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.commentReply = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentReplyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CommentReply();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.commentReply = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentReplyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
