/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { ArticalDetailComponent } from '../../../../../../main/webapp/app/entities/artical/artical-detail.component';
import { ArticalService } from '../../../../../../main/webapp/app/entities/artical/artical.service';
import { Artical } from '../../../../../../main/webapp/app/entities/artical/artical.model';

describe('Component Tests', () => {

    describe('Artical Management Detail Component', () => {
        let comp: ArticalDetailComponent;
        let fixture: ComponentFixture<ArticalDetailComponent>;
        let service: ArticalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalDetailComponent],
                providers: [
                    ArticalService
                ]
            })
            .overrideTemplate(ArticalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Artical(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.artical).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
