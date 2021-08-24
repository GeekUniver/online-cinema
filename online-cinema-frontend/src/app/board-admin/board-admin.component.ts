import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import {Genre} from "../Genre";
import {Country} from "../../../Country";
import {DataService} from "../data.service";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.scss']
})
export class BoardAdminComponent implements OnInit {
  isError: boolean = false;
  genres: Genre[] = [];
  countries: Country[] = [];
  genre: string = '';
  country: string = '';


  constructor(private dataService: DataService) { }

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

  addGenre() {
    console.log(this.genre);
    let newGenre: Genre = new Genre(this.genre);
    this.dataService.addNewGenre(newGenre)
      .then(res => {
        this.isError = false;
      })
      .catch(err => {
        this.isError = true;
      });
  }

  addCountry() {
    console.log(this.genre);
    let newCountry: Country = new Country(this.country);
    this.dataService.addNewCountry(newCountry)
      .then(res => {
        this.isError = false;
      })
      .catch(err => {
        this.isError = true;
      });
  }
}
