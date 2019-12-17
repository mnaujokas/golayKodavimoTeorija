import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVector } from 'app/shared/model/vector.model';

@Component({
  selector: 'jhi-vector-detail',
  templateUrl: './vector-detail.component.html'
})
export class VectorDetailComponent implements OnInit {
  vector: IVector;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vector }) => {
      this.vector = vector;
    });
  }

  previousState() {
    window.history.back();
  }
}
