import {Component, OnInit} from '@angular/core';
import {DataService} from "../data.service";
import {Router} from "@angular/router";
import {Genre} from "../Genre";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Country} from "../../../Country";

@Component({
  selector: 'app-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.scss']
})
export class VideoUploadComponent implements OnInit {

  isError: boolean = false;
  genres: Genre[] = [];
  currentGenres: Array<Number> = [];
  countries: Country[] = [];
  currentCountries: Array<Number> = [];

  form: FormGroup;

  constructor(private fb: FormBuilder, private dataService: DataService, private router: Router) {
    this.form = this.fb.group({
      checkCountryArray: this.fb.array([], [Validators.required])
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
    let form: HTMLFormElement | null = document.forms.namedItem('uploadForm');
    if (form) {
      let fd = new FormData(form);

      console.log(JSON.stringify(this.currentGenres))
      console.log(this.currentGenres.toString())

      fd.append(
        'genreList', (this.currentGenres.toString()));
      fd.append(
        'countryList', (this.currentCountries.toString()));

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

  onCheckboxChange(e: any) {
    const index = e.target.value;

    if (e.target.checked) {
      if (index <= this.genres.length) {
        this.currentGenres.push(index);
      } else {
        this.currentCountries.push((+index - this.genres.length));
      }
    } else {
      if (index <= this.genres.length) {
        this.remove(this.currentGenres, index);
      } else {
        this.remove(this.currentCountries, index);
      }
    }
  }

  remove(arr: Array<Number>, key: number) {
    const index = arr.indexOf(key, 0);
    if (index > -1) {
      arr.splice(index, 1);
    }
  }
}
