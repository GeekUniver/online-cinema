import {Component} from '@angular/core';
import {VideoGalleryComponent} from "../video-gallery/video-gallery.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent extends VideoGalleryComponent {
  // fileName: string = "Hello";

  search() {
    console.log("Компонент = " + this.searchTemplate);
    this.searchTemplate = "sds"
    super.update();
  }
}
