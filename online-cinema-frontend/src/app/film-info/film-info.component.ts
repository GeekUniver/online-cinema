import {Component, OnInit} from '@angular/core';
import {VideoMetadata} from "../video-metadata";
import {DataService} from "../data.service";
import {ActivatedRoute} from "@angular/router";
import { TokenStorageService } from '../_services/token-storage.service';
import {CommentRepr} from "../commentrepr";

@Component({
  selector: 'app-film-info',
  templateUrl: './film-info.component.html',
  styleUrls: ['./film-info.component.scss']
})
export class FilmInfoComponent implements OnInit {
  isError: boolean = false;
  currentUser: any;
  videoId: number = 0;
  rating: number = 0;
  comments: CommentRepr[] = [];
  values: number [] = [1,2,3,4,5];
  public videoMetadata: VideoMetadata  = new VideoMetadata(0, '', '', '', '', '',0, [], []);
  constructor(private route: ActivatedRoute, public dataService: DataService, private tokenStorageService: TokenStorageService) { }


  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.videoId = param.id;
      this.dataService.findById(param.id)
        .then((vmd) => {
          this.videoMetadata = vmd;
          this.isError = false;
        })
        .catch(err => {
          this.isError = true;
        });
    });
    this.getCommentsByVideoId(this.videoId)
    this.currentUser = this.tokenStorageService.getUser();
  }

  submit() {
      let form:HTMLFormElement | null = document.forms.namedItem('commentForm');
      if (form) {
        let fd = new FormData(form);
        this.dataService.addNewComment(fd)
          .then(() => {
            this.isError = false;
          })
          .catch((err) => {
            this.isError = true;
            console.error(err);
          });
      }
      this.getCommentsByVideoId(this.videoId);
  }

  getCommentsByVideoId(videoId : number) {
    this.dataService.findCommentsByVideoId(this.videoId)
      .then((res) => {
            this.isError = false;
            this.comments = res;
          })
          .catch((err) => {
            this.isError = true;
          });
  }

  makeRating() {
   // console.info(this.rating);
    //console.info('video_rating: ' + video_rating);
    //console.info('video id: ' + this.videoMetadata.id);
   // console.info('user id: ' + currentUser.)

  }

  onChange = (value: any) => {};


setRate(rate: number) {
    this.rating = rate;
    console.info(this.rating);
    this.onChange(rate);
}


}


