import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVector } from 'app/shared/model/vector.model';
import { VectorService } from './vector.service';
import { VectorDeleteDialogComponent } from './vector-delete-dialog.component';

@Component({
  selector: 'jhi-vector',
  templateUrl: './vector.component.html'
})
export class VectorComponent implements OnInit, OnDestroy {
  vectors: IVector[];
  eventSubscriber: Subscription;

  constructor(protected vectorService: VectorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.vectorService.query().subscribe((res: HttpResponse<IVector[]>) => {
      this.vectors = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInVectors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVector) {
    return item.id;
  }

  registerChangeInVectors() {
    this.eventSubscriber = this.eventManager.subscribe('vectorListModification', () => this.loadAll());
  }

  delete(vector: IVector) {
    const modalRef = this.modalService.open(VectorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vector = vector;
  }
}
