import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GolayTestModule } from '../../../test.module';
import { VectorDetailComponent } from 'app/entities/vector/vector-detail.component';
import { Vector } from 'app/shared/model/vector.model';

describe('Component Tests', () => {
  describe('Vector Management Detail Component', () => {
    let comp: VectorDetailComponent;
    let fixture: ComponentFixture<VectorDetailComponent>;
    const route = ({ data: of({ vector: new Vector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [VectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
