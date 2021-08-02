import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { VideoGalleryComponent } from './video-gallery/video-gallery.component';
import { VideoPlayerComponent } from './video-player/video-player.component';
import { VideoUploadComponent } from './video-upload/video-upload.component';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    VideoGalleryComponent,
    VideoPlayerComponent,
    VideoUploadComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
