import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { DataService } from "../data.service";
import {VideoMetadata} from "../video-metadata";

@Component({
  selector: 'app-video-gallery',
  templateUrl: './video-gallery.component.html',
  styleUrls: ['./video-gallery.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class VideoGalleryComponent implements OnInit {

  previews: VideoMetadata[] = [];
  isError: boolean = false;
  searchTemplate: string = "";

  constructor(public dataService: DataService) {}

  ngOnInit(): void {
    this.update();
  }

  update(): void {
    console.log(this.searchTemplate)
    this.dataService.findAllPreviews(this.searchTemplate)
      .then(res => {
        this.isError = false;
        this.previews = res.content;
        console.log(this.previews.length);
      })
      .catch(err => {
        this.isError = true;
      });
  }
}
