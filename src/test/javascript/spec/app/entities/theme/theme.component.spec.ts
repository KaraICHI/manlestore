/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManleTestModule } from '../../../test.module';
import { ThemeComponent } from '../../../../../../main/webapp/app/entities/theme/theme.component';
import { ThemeService } from '../../../../../../main/webapp/app/entities/theme/theme.service';
import { Theme } from '../../../../../../main/webapp/app/entities/theme/theme.model';

describe('Component Tests', () => {

    describe('Theme Management Component', () => {
        let comp: ThemeComponent;
        let fixture: ComponentFixture<ThemeComponent>;
        let service: ThemeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ManleTestModule],
                declarations: [ThemeComponent],
                providers: [
                    ThemeService
                ]
            })
            .overrideTemplate(ThemeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThemeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThemeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Theme(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.themes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
