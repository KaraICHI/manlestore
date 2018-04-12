/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { ArticalComponent } from '../../../../../../main/webapp/app/entities/artical/artical.component';
import { ArticalService } from '../../../../../../main/webapp/app/entities/artical/artical.service';
import { Artical } from '../../../../../../main/webapp/app/entities/artical/artical.model';

describe('Component Tests', () => {

    describe('Artical Management Component', () => {
        let comp: ArticalComponent;
        let fixture: ComponentFixture<ArticalComponent>;
        let service: ArticalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ArticalComponent],
                providers: [
                    ArticalService
                ]
            })
            .overrideTemplate(ArticalComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Artical(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.articals[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
