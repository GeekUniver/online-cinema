import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {VideoGalleryComponent} from "./video-gallery/video-gallery.component";
import {VideoPlayerComponent} from "./video-player/video-player.component";
import {VideoUploadComponent} from "./video-upload/video-upload.component";
import {BoardAdminComponent} from "./board-admin/board-admin.component";
import {BoardUserComponent} from "./board-user/board-user.component";
import {ProfileComponent} from "./profile/profile.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {BoardModeratorComponent} from "./board-moderator/board-moderator.component";


const routes: Routes = [
  {path:  "", pathMatch:  "full", redirectTo:  "gallery"},
  {path: "gallery", component: VideoGalleryComponent},
  {path: "player/:id", component: VideoPlayerComponent},
  {path: "upload", component: VideoUploadComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
/*
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

 */
