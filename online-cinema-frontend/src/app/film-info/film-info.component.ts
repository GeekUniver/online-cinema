import {Component, OnInit} from '@angular/core';
import {VideoMetadata} from "../video-metadata";
import {DataService} from "../data.service";
import {ActivatedRoute} from "@angular/router";
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-film-info',
  templateUrl: './film-info.component.html',
  styleUrls: ['./film-info.component.scss']
})
export class FilmInfoComponent implements OnInit {
  isError: boolean = false;
  currentUser: any;
  public videoMetadata: VideoMetadata  = new VideoMetadata(0, '', '', '', '', '',0);
  constructor(private route: ActivatedRoute, public dataService: DataService, private token: TokenStorageService) { }

  ngOnInit(): void {

   this.currentUser = this.token.getUser();

   this.route.params.subscribe(param => {
      console.log(param)
      this.dataService.findById(param.id)
        .then((vmd) => {
          this.videoMetadata = vmd;
          this.isError = false;
        })
        .catch(err => {
          this.isError = true;
        });
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





