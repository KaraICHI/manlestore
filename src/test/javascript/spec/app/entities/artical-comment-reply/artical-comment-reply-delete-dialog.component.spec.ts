/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentReplyDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply-delete-dialog.component';
import { ArticalCommentReplyService } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.service';

describe('Component Tests', () => {

    describe('ArticalCommentReply Management Delete Component', () => {
        let comp: ArticalCommentReplyDeleteDialogComponent;
        let fixture: ComponentFixture<ArticalCommentReplyDeleteDialogComponent>;
        let service: ArticalCommentReplyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentReplyDeleteDialogComponent],
                providers: [
                    ArticalCommentReplyService
                ]
            })
            .overrideTemplate(ArticalCommentReplyDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentReplyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentReplyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
