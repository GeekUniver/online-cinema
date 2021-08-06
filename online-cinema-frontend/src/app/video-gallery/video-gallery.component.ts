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

  previews: VideoMetadata[] = [];
  isError: boolean = false;
  content!: string;

  constructor(public dataService: DataService, private userService: UserService) {}  // добавлен private userService: UserService

  ngOnInit(): void {
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
/*
import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  content: string;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

}
 */
