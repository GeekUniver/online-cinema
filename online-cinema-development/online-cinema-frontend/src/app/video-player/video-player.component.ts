import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DataService} from "../data.service";
import {ActivatedRoute} from "@angular/router";
import {VideoMetadata} from "../video-metadata";

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.scss']
})
export class VideoPlayerComponent implements OnInit {

  isError: boolean = false;

  public videoMetadata: VideoMetadata  = new VideoMetadata(0, '', '', '', '');

  @ViewChild("videoPlayer") videoPlayerRef!: ElementRef;

  constructor(public dataService: DataService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      console.log(param)
      this.dataService.findById(param.id)
        .then((vmd) => {
          this.videoMetadata = vmd;

          let videoPlayer = this.videoPlayerRef.nativeElement;
          videoPlayer.load();

          let currentTime = sessionStorage.getItem("currentTime");
          let currentFilmId = sessionStorage.getItem("currentFilmId")
          if (currentTime || currentFilmId) {
            if (currentFilmId == String(this.videoMetadata.id)) {
              videoPlayer.currentTime = currentTime;
            }
          }

          videoPlayer.ontimeupdate = () => {
            sessionStorage.setItem("currentTime", videoPlayer.currentTime);
            sessionStorage.setItem("currentFilmId", String(this.videoMetadata.id))
          }
        })
    })
  }
  submit() {
    console.log("Submit button to add a new comment.");
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
  }
}
