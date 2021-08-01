import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {VideoGalleryComponent} from "./video-gallery/video-gallery.component";
import {VideoPlayerComponent} from "./video-player/video-player.component";
import {VideoUploadComponent} from "./video-upload/video-upload.component";
import { AdminNavComponent } from './components/admin/admin-nav/admin-nav.component';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { UserDashboardComponent } from './components/admin/user-dashboard/user-dashboard.component';
import { MovieDashboardComponent } from './components/admin/movie-dashboard/movie-dashboard.component';

const routes: Routes = [
  {path:  "", pathMatch:  "full", redirectTo:  "gallery"},
  {path: "gallery", component: VideoGalleryComponent},
  {path: "player/:id", component: VideoPlayerComponent},
  {path: "upload", component: VideoUploadComponent},
  {path: 'admin', component: AdminLoginComponent},
  {
    path: 'dashboard', component: AdminNavComponent,
    children: [
      { path: '', redirectTo: 'user', pathMatch: 'full' },
      { path: 'user', component: UserDashboardComponent },
      { path: 'movie', component: MovieDashboardComponent },
    ]
  },
  {path: '**', redirectTo: ''},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
