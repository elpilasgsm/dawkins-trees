import {Injectable} from '@angular/core';
import {Observable} from "rxjs/index";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Tree} from "./tree";
import {stringify} from "querystring";

@Injectable({
  providedIn: 'root'
})
export class TreesService {

  constructor(private httpClient: HttpClient) {
  }

  public get(url: string): Observable<Tree[]> {
    return this.httpClient.get<Tree[]>(url);
  }

  public getChildren(length: number, minLength: number, lengthFactor: number, widthFactor: number, angleFactor: number, width: number, maxLevel: number): Observable<Tree[]> {
    return this.httpClient.get<Tree[]>("/image/children/"
      + length
      + "/" + minLength
      + "/" + lengthFactor
      + "/" + widthFactor
      + "/" + angleFactor
      + "/" + width
      + "/" + maxLevel);
  }

  public getRandom(num: number): Observable<Tree[]> {
    let params = new HttpParams().set("number", num.toString());
    return this.httpClient.get<Tree[]>("/image", {params: params}
    );
  }

  public crossover(request: Tree[]): Observable<Tree[]> {
    return this.httpClient.post<Tree[]>("/image/cross", request);
  }
}
