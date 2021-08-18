import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {VideoMetadata} from "./video-metadata";
import {CommentRepr} from "./commentrepr";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public condition$ = new Subject<string>();

  constructor(private http: HttpClient) {
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
    return this.http.post('/api/v1/video/upload', formData).toPromise()
  }

  findAllPreviewsWithCondition(condition: string) {
    condition = condition.trim();
    if (condition === "") {
      return this.findAllPreviews();
    }

    return this.http.get<VideoMetadata[]>('/api/v1/video/search/' + condition).toPromise();
  }

  public addNewComment(formData: FormData) {
    return this.http.post('/api/v1/video/addNewComment', formData).toPromise();
  }

   public findAllComments() {
      return this.http.get<CommentRepr[]>('/api/v1/video/comments').toPromise()
    }

}
