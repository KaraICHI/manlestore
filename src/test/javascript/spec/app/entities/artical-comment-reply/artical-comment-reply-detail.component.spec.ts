/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentReplyDetailComponent } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply-detail.component';
import { ArticalCommentReplyService } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.service';
import { ArticalCommentReply } from '../../../../../../main/webapp/app/entities/artical-comment-reply/artical-comment-reply.model';

describe('Component Tests', () => {

    describe('ArticalCommentReply Management Detail Component', () => {
        let comp: ArticalCommentReplyDetailComponent;
        let fixture: ComponentFixture<ArticalCommentReplyDetailComponent>;
        let service: ArticalCommentReplyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentReplyDetailComponent],
                providers: [
                    ArticalCommentReplyService
                ]
            })
            .overrideTemplate(ArticalCommentReplyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentReplyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentReplyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ArticalCommentReply(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.articalCommentReply).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
