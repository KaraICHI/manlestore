/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { OrderCommentComponent } from '../../../../../../main/webapp/app/entities/order-comment/order-comment.component';
import { OrderCommentService } from '../../../../../../main/webapp/app/entities/order-comment/order-comment.service';
import { OrderComment } from '../../../../../../main/webapp/app/entities/order-comment/order-comment.model';

describe('Component Tests', () => {

    describe('OrderComment Management Component', () => {
        let comp: OrderCommentComponent;
        let fixture: ComponentFixture<OrderCommentComponent>;
        let service: OrderCommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [OrderCommentComponent],
                providers: [
                    OrderCommentService
                ]
            })
            .overrideTemplate(OrderCommentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderCommentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderCommentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OrderComment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.orderComments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
