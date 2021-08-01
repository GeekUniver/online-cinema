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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './app-material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AdminNavComponent, SidenavService } from './components/admin/admin-nav/admin-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { LeftMenuComponent, SelectMenuService } from './components/admin/left-menu/left-menu.component';
import { FormInputComponent } from './components/abstracts/abstract-form/form-input.component';
import { AbstractFormDirective } from './components/abstracts/abstract-form/abstract-form.directive';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { UserDashboardComponent } from './components/admin/user-dashboard/user-dashboard.component';
import { FormModalComponent } from './components/abstracts/dialog/form-modal.component';
import { FormImageInputComponent } from './components/abstracts/abstract-form/form-image-input.component';
import { AvatarModule } from 'ngx-avatar';
import { MaterialFileInputModule } from 'ngx-material-file-input';

import { ConfirmDialogComponent } from './components/abstracts/dialog/confirm-dialog.component';
import { MovieDashboardComponent } from './components/admin/movie-dashboard/movie-dashboard.component';

import { NgxMatDatetimePickerModule, NgxMatTimepickerModule, NgxMatNativeDateModule } from '@angular-material-components/datetime-picker';

import { FormDatePickerComponent } from './components/abstracts/abstract-form/form-datepicker.component';
import { FormSelectComponent } from './components/abstracts/abstract-form/form-select.component';

import { MomentModule } from 'ngx-moment';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/br';

registerLocaleData(locale);

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    VideoGalleryComponent,
    VideoPlayerComponent,
    VideoUploadComponent,
    AdminNavComponent,
    // ChairsComponent,
    LeftMenuComponent,
    FormInputComponent,
    FormDatePickerComponent,
    AbstractFormDirective,
    FormSelectComponent,
    AdminLoginComponent,
    UserDashboardComponent,
    FormModalComponent,
    FormImageInputComponent,
    ConfirmDialogComponent,
    MovieDashboardComponent,
  ],
  entryComponents: [FormInputComponent, FormDatePickerComponent, FormSelectComponent, FormImageInputComponent],
  imports: [
    MaterialFileInputModule,
    AvatarModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    FlexLayoutModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    HttpClientModule,
    NgxMatDatetimePickerModule,
    NgxMatTimepickerModule,
    NgxMatNativeDateModule,
    MomentModule
  ],
  providers: [SidenavService, SelectMenuService],
  bootstrap: [AppComponent]
})
export class AppModule { }
