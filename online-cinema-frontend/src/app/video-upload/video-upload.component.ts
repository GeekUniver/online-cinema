import { Component, OnInit } from '@angular/core';
import { DataService } from "../data.service";
import {Router} from "@angular/router";
import {Genre} from "../Genre";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthInterceptor} from "../ _helpers/auth.interceptor";
import {Country} from "../../../Country";

@Component({
  selector: 'app-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.scss']
})
export class VideoUploadComponent implements OnInit {

  isError: boolean = false;
  genres: Genre[] = [];
  currentGenres: Genre[] = [];
  countries: Country[] = [];
  currentCountries: Country[] = [];

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

    this.dataService.findAllCountries()
      .then(res => {
        this.isError = false;
        this.countries = res;
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

      fd.append(
        'genreList', JSON.stringify(this.currentGenres));
      // fd.append(
      //   'countryList', JSON.stringify(this.currentCountries));

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

  onGenreCheckboxChange(e: any) {
    const checkArray: FormArray = this.form.get('checkArray') as FormArray;
    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
    } else {
      const index = checkArray.controls.findIndex(x => x.value === e.target.value);
      checkArray.removeAt(index);
    }
    this.currentGenres = this.form.value;
  }

  onCountryCheckboxChange(e: any) {
    const checkArray: FormArray = this.form.get('checkArray') as FormArray;

    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
    } else {
      const index = checkArray.controls.findIndex(x => x.value === e.target.value);
      checkArray.removeAt(index);
    }
    this.currentCountries = this.form.value;
  }
}
