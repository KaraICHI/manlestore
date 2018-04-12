/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentDetailComponent } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment-detail.component';
import { ArticalCommentService } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.service';
import { ArticalComment } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.model';

describe('Component Tests', () => {

    describe('ArticalComment Management Detail Component', () => {
        let comp: ArticalCommentDetailComponent;
        let fixture: ComponentFixture<ArticalCommentDetailComponent>;
        let service: ArticalCommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentDetailComponent],
                providers: [
                    ArticalCommentService
                ]
            })
            .overrideTemplate(ArticalCommentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ArticalComment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.articalComment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
