/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManleTestModule } from '../../../test.module';
import { ThemeDetailComponent } from '../../../../../../main/webapp/app/entities/theme/theme-detail.component';
import { ThemeService } from '../../../../../../main/webapp/app/entities/theme/theme.service';
import { Theme } from '../../../../../../main/webapp/app/entities/theme/theme.model';

describe('Component Tests', () => {

    describe('Theme Management Detail Component', () => {
        let comp: ThemeDetailComponent;
        let fixture: ComponentFixture<ThemeDetailComponent>;
        let service: ThemeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ThemeDetailComponent],
                providers: [
                    ThemeService
                ]
            })
            .overrideTemplate(ThemeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThemeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThemeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Theme(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.theme).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
