import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IText } from 'app/shared/model/text.model';

type EntityResponseType = HttpResponse<IText>;
type EntityArrayResponseType = HttpResponse<IText[]>;

@Injectable({ providedIn: 'root' })
export class TextService {
  public resourceUrl = SERVER_API_URL + 'api/texts';

  constructor(protected http: HttpClient) {}

  create(text: IText): Observable<EntityResponseType> {
    return this.http.post<IText>(this.resourceUrl, text, { observe: 'response' });
  }

  update(text: IText): Observable<EntityResponseType> {
    return this.http.put<IText>(this.resourceUrl, text, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IText>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IText[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
