import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GolayTestModule } from '../../../test.module';
import { TextDetailComponent } from 'app/entities/text/text-detail.component';
import { Text } from 'app/shared/model/text.model';

describe('Component Tests', () => {
  describe('Text Management Detail Component', () => {
    let comp: TextDetailComponent;
    let fixture: ComponentFixture<TextDetailComponent>;
    const route = ({ data: of({ text: new Text(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [TextDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TextDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TextDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.text).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
