import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Text } from 'app/shared/model/text.model';
import { TextService } from './text.service';
import { TextComponent } from './text.component';
import { TextDetailComponent } from './text-detail.component';
import { TextUpdateComponent } from './text-update.component';
import { IText } from 'app/shared/model/text.model';

@Injectable({ providedIn: 'root' })
export class TextResolve implements Resolve<IText> {
  constructor(private service: TextService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IText> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((text: HttpResponse<Text>) => text.body));
    }
    return of(new Text());
  }
}

export const textRoute: Routes = [
  {
    path: '',
    component: TextComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Texts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TextDetailComponent,
    resolve: {
      text: TextResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Texts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TextUpdateComponent,
    resolve: {
      text: TextResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Texts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TextUpdateComponent,
    resolve: {
      text: TextResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Texts'
    },
    canActivate: [UserRouteAccessService]
  }
];
