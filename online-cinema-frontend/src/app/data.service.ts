import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {VideoMetadata} from "./video-metadata";
import {Page} from "./page";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) {
  }

  public findById(id: number) {
    return this.http.get<VideoMetadata>('/api/v1/video/' + id).toPromise()
  }

  public findAllPreviews(fileName: string) {
    const params = new HttpParams()
      .set('fileName', fileName);

    return this.http.get<Page>('/api/v1/video/all', {params}).toPromise();
  }

  public uploadNewVideo(formData: FormData) {
    return this.http.post('/api/v1/video/upload', formData).toPromise()
  }

  // update(): VideoMetadata[] {
  //
  //   this.findAllPreviews()
  //     .then(res => {
  //       this.previews = res.content;
  //     })
  //     .catch(err => {
  //     });
  // }
}
