/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { CommentReplyDetailComponent } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply-detail.component';
import { CommentReplyService } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.service';
import { CommentReply } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.model';

describe('Component Tests', () => {

    describe('CommentReply Management Detail Component', () => {
        let comp: CommentReplyDetailComponent;
        let fixture: ComponentFixture<CommentReplyDetailComponent>;
        let service: CommentReplyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [CommentReplyDetailComponent],
                providers: [
                    CommentReplyService
                ]
            })
            .overrideTemplate(CommentReplyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentReplyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentReplyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CommentReply(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.commentReply).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
