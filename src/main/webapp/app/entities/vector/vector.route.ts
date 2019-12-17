import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vector } from 'app/shared/model/vector.model';
import { VectorService } from './vector.service';
import { VectorComponent } from './vector.component';
import { VectorDetailComponent } from './vector-detail.component';
import { VectorUpdateComponent } from './vector-update.component';
import { IVector } from 'app/shared/model/vector.model';

@Injectable({ providedIn: 'root' })
export class VectorResolve implements Resolve<IVector> {
  constructor(private service: VectorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVector> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vector: HttpResponse<Vector>) => vector.body));
    }
    return of(new Vector());
  }
}

export const vectorRoute: Routes = [
  {
    path: '',
    component: VectorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vectors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VectorDetailComponent,
    resolve: {
      vector: VectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vectors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VectorUpdateComponent,
    resolve: {
      vector: VectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vectors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VectorUpdateComponent,
    resolve: {
      vector: VectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vectors'
    },
    canActivate: [UserRouteAccessService]
  }
];
