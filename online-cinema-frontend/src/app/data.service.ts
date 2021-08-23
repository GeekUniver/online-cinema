import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {VideoMetadata} from "./video-metadata";
import {CommentRepr} from "./commentrepr";
import {Genre} from "./Genre";
import {AuthInterceptor} from "./ _helpers/auth.interceptor";
import {TokenStorageService} from "./_services/token-storage.service";
import {Subject} from "rxjs";
import {Country} from "../../Country";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public condition$ = new Subject<string>();

  constructor(private http: HttpClient,  private authInterceptor: AuthInterceptor, private tokenStorageService: TokenStorageService) {

  }



  public changeCount(condition: string) {
    this.condition$.next(condition);
    console.log("data condition = " + this.condition$);
  }

  public findById(id: number) {
    return this.http.get<VideoMetadata>('/api/v1/video/' + id).toPromise()
  }

  public findAllPreviews() {
    //let header =  new HttpHeaders({ 'Authorization': 'Bearer ' + this.tokenStorageService.getToken()});

   // return this.http.get<VideoMetadata[]>('/api/v1/video/all', {headers: header}).toPromise();
    return this.http.get<VideoMetadata[]>('/api/v1/video/all').toPromise()
  }

  public uploadNewVideo(formData: FormData) {
    return this.http.post('/api/v1/admin/upload', formData, ).toPromise()
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

  public addNewComment(formData: FormData) {
    return this.http.post('/api/v1/video/addNewComment', formData).toPromise();
  }

  public findAllComments() {
     return this.http.get<CommentRepr[]>('/api/v1/video/comments').toPromise()
  }

  public findCommentsByVideoId(id : number) {
     return this.http.get<CommentRepr[]>('/api/v1/video/comments/' + id).toPromise()
  }

  findAllCountries() {
    return this.http.get<Country[]>('/api/v1/admin/countries').toPromise();
  }
}
