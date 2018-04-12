/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentReplyComponent } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.component';
import { ArticalCommentReplyService } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.service';
import { ArticalCommentReply } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.model';

describe('Component Tests', () => {

    describe('ArticalCommentReply Management Component', () => {
        let comp: ArticalCommentReplyComponent;
        let fixture: ComponentFixture<ArticalCommentReplyComponent>;
        let service: ArticalCommentReplyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentReplyComponent],
                providers: [
                    ArticalCommentReplyService
                ]
            })
            .overrideTemplate(ArticalCommentReplyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentReplyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentReplyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ArticalCommentReply(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.articalCommentReplies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
