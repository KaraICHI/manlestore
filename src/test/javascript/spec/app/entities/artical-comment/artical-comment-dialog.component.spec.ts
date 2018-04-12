/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentDialogComponent } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment-dialog.component';
import { ArticalCommentService } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.service';
import { ArticalComment } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.model';
import { ArticalService } from '../../../../../../main/webapp/app/entities/artical';
import { ClientUserService } from '../../../../../../main/webapp/app/entities/client-user';

describe('Component Tests', () => {

    describe('ArticalComment Management Dialog Component', () => {
        let comp: ArticalCommentDialogComponent;
        let fixture: ComponentFixture<ArticalCommentDialogComponent>;
        let service: ArticalCommentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentDialogComponent],
                providers: [
                    ArticalService,
                    ClientUserService,
                    ArticalCommentService
                ]
            })
            .overrideTemplate(ArticalCommentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ArticalComment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.articalComment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'articalCommentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ArticalComment();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.articalComment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'articalCommentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
