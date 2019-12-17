import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVector } from 'app/shared/model/vector.model';

type EntityResponseType = HttpResponse<IVector>;
type EntityArrayResponseType = HttpResponse<IVector[]>;

@Injectable({ providedIn: 'root' })
export class VectorService {
  public resourceUrl = SERVER_API_URL + 'api/vectors';

  constructor(protected http: HttpClient) {}

  create(vector: IVector): Observable<EntityResponseType> {
    return this.http.post<IVector>(this.resourceUrl, vector, { observe: 'response' });
  }

  update(vector: IVector): Observable<EntityResponseType> {
    return this.http.put<IVector>(this.resourceUrl, vector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
