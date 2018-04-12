/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { ArticalCommentComponent } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.component';
import { ArticalCommentService } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.service';
import { ArticalComment } from '../../../../../../main/webapp/app/entities/artical-comment/artical-comment.model';

describe('Component Tests', () => {

    describe('ArticalComment Management Component', () => {
        let comp: ArticalCommentComponent;
        let fixture: ComponentFixture<ArticalCommentComponent>;
        let service: ArticalCommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalCommentComponent],
                providers: [
                    ArticalCommentService
                ]
            })
            .overrideTemplate(ArticalCommentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalCommentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalCommentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ArticalComment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.articalComments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
