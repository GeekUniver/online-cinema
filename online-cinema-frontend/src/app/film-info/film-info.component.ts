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
  comments: CommentRepr[] = [];
  public videoMetadata: VideoMetadata  = new VideoMetadata(0, '', '', '', '', '',0, [], []);
  constructor(private route: ActivatedRoute, public dataService: DataService, private tokenStorageService: TokenStorageService) { }
//private tokenStorageService: TokenStorageService в конструкторе на 20.08 не было

  ngOnInit(): void {
    //this.currentUser = this.tokenStorageService.getUser()
    //console.log(this.currentUser)
    //console.log(this.currentUser.username)
    this.route.params.subscribe(param => {
      this.dataService.findById(param.id)
        .then((vmd) => {
          this.videoMetadata = vmd;
          this.isError = false;
        })
        .catch(err => {
          this.isError = true;
        });
    });
    this.getCommentsByVideoId(this.videoId);
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

}


