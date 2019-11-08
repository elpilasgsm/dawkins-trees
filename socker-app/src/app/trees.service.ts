import {Injectable} from '@angular/core';
import {Observable} from "rxjs/index";
import {HttpClient} from "@angular/common/http";
import {Tree} from "./tree";

@Injectable({
  providedIn: 'root'
})
export class TreesService {

  constructor(private httpClient: HttpClient) {
  }

  public get(url: string): Observable<Tree[]> {
    return this.httpClient.get<Tree[]>(url);
  }
}
