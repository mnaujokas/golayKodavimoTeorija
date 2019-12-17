import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IText } from 'app/shared/model/text.model';
import { TextService } from './text.service';
import { TextDeleteDialogComponent } from './text-delete-dialog.component';

@Component({
  selector: 'jhi-text',
  templateUrl: './text.component.html'
})
export class TextComponent implements OnInit, OnDestroy {
  texts: IText[];
  eventSubscriber: Subscription;

  constructor(protected textService: TextService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.textService.query().subscribe((res: HttpResponse<IText[]>) => {
      this.texts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTexts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IText) {
    return item.id;
  }

  registerChangeInTexts() {
    this.eventSubscriber = this.eventManager.subscribe('textListModification', () => this.loadAll());
  }

  delete(text: IText) {
    const modalRef = this.modalService.open(TextDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.text = text;
  }
}
