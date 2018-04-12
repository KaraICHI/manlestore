/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { CommentReplyComponent } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.component';
import { CommentReplyService } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.service';
import { CommentReply } from '../../../../../../main/webapp/app/entities/comment-reply/comment-reply.model';

describe('Component Tests', () => {

    describe('CommentReply Management Component', () => {
        let comp: CommentReplyComponent;
        let fixture: ComponentFixture<CommentReplyComponent>;
        let service: CommentReplyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [CommentReplyComponent],
                providers: [
                    CommentReplyService
                ]
            })
            .overrideTemplate(CommentReplyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentReplyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentReplyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CommentReply(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.commentReplies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
