import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { VideoGalleryComponent } from './video-gallery/video-gallery.component';
import { VideoPlayerComponent } from './video-player/video-player.component';
import { VideoUploadComponent } from './video-upload/video-upload.component';
import {FormsModule} from "@angular/forms";
import { TestComponent } from './test/test.component';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    VideoGalleryComponent,
    VideoPlayerComponent,
    VideoUploadComponent,
    TestComponent
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
