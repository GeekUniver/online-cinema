import { Component, OnInit } from '@angular/core';
import { DataService } from "../data.service";
import {Router} from "@angular/router";
import {Genre} from "../Genre";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthInterceptor} from "../ _helpers/auth.interceptor";

@Component({
  selector: 'app-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.scss']
})
export class VideoUploadComponent implements OnInit {

  isError: boolean = false;
  genres: Genre[] = [];
  form: FormGroup;

  constructor(private fb: FormBuilder, private dataService: DataService, private router: Router) {
    this.form = this.fb.group({
      checkArray: this.fb.array([], [Validators.required])
    })
  }

  ngOnInit(): void {
    this.dataService.findAllGenres()
      .then(res => {
        this.isError = false;
        this.genres = res;
      })
      .catch(err => {
        this.isError = true;
      });
  }

  submit() {
    console.log("Submit button")
    let form:HTMLFormElement | null = document.forms.namedItem('uploadForm');
    if (form) {
      let fd = new FormData(form);
      this.dataService.uploadNewVideo(fd)
        .then(() => {
          this.isError = false;
          this.router.navigate(['player/1']);
        })
        .catch((err) => {
          this.isError = true;
          console.error(err);
        });
    }
  }
}
