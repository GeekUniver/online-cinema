import {Component, OnInit} from '@angular/core';
import {VideoMetadata} from "../video-metadata";
import {DataService} from "../data.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-film-info',
  templateUrl: './film-info.component.html',
  styleUrls: ['./film-info.component.scss']
})
export class FilmInfoComponent implements OnInit {
  isError: boolean = false;
  public videoMetadata: VideoMetadata  = new VideoMetadata(0, '', '', '', '', '',0, [], []);
  constructor(private route: ActivatedRoute, public dataService: DataService) { }

  ngOnInit(): void {
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
}


