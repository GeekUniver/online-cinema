import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { DataService } from "../data.service";
import {VideoMetadata} from "../video-metadata";
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-video-gallery',
  templateUrl: './video-gallery.component.html',
  styleUrls: ['./video-gallery.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class VideoGalleryComponent implements OnInit {
  condition: string = "";
  previews: VideoMetadata[] = [];
  isError: boolean = false;
  content!: string;

  constructor(public dataService: DataService, private userService: UserService) {}  // добавлен private userService: UserService

  ngOnInit(): void {
    this.dataService.condition$.subscribe((condition) => this.setCondition(condition));
    this.update(this.condition);
  }

  setCondition(condition: string) {
    this.condition = condition;
    console.log("video condition: " + this.condition);
    this.update(condition);
  }

  update(condition: string) {
    this.dataService.findAllPreviewsWithCondition(this.condition)
    ////////////
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;

      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    /////////удалить можно выше
    this.dataService.findAllPreviews()
      .then(res => {
        this.isError = false;
        this.previews = res;
      })
      .catch(err => {
        this.isError = true;
      });
  }

}
