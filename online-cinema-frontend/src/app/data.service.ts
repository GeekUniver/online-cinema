import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {VideoMetadata} from "./video-metadata";
import {Subject} from "rxjs";
import {Genre} from "./Genre";
import {AuthInterceptor} from "./ _helpers/auth.interceptor";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public condition$ = new Subject<string>();

  constructor(private http: HttpClient,  private authInterceptor: AuthInterceptor) {
  }

  public changeCount(condition: string) {
    this.condition$.next(condition);
    console.log("data condition = " + this.condition$);
  }

  public findById(id: number) {
    return this.http.get<VideoMetadata>('/api/v1/video/' + id).toPromise()
  }

  public findAllPreviews() {
    return this.http.get<VideoMetadata[]>('/api/v1/video/all').toPromise()
  }

  public uploadNewVideo(formData: FormData) {
    return this.http.post('/api/v1/admin/upload', formData).toPromise()
  }

  findAllPreviewsWithCondition(condition: string) {
    condition = condition.trim();
    if (condition === "") {
      return this.findAllPreviews();
    }

    return this.http.get<VideoMetadata[]>('/api/v1/video/search/' + condition).toPromise();
  }

  findAllGenres() {
    return this.http.get<Genre[]>('/api/v1/admin/genres').toPromise();
  }
}
