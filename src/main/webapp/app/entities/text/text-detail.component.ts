import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IText } from 'app/shared/model/text.model';

@Component({
  selector: 'jhi-text-detail',
  templateUrl: './text-detail.component.html'
})
export class TextDetailComponent implements OnInit {
  text: IText;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ text }) => {
      this.text = text;
    });
  }

  previousState() {
    window.history.back();
  }
}
