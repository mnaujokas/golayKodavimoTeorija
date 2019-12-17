import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GolayTestModule } from '../../../test.module';
import { VectorComponent } from 'app/entities/vector/vector.component';
import { VectorService } from 'app/entities/vector/vector.service';
import { Vector } from 'app/shared/model/vector.model';

describe('Component Tests', () => {
  describe('Vector Management Component', () => {
    let comp: VectorComponent;
    let fixture: ComponentFixture<VectorComponent>;
    let service: VectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GolayTestModule],
        declarations: [VectorComponent],
        providers: []
      })
        .overrideTemplate(VectorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VectorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VectorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vector(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vectors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
