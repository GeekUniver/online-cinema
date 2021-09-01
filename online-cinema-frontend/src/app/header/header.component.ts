import {Component,  OnInit} from '@angular/core';
import {DataService} from "../data.service";
import { TokenStorageService } from '../_services/token-storage.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit  {
  condition: string ="";
  roles!: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username!: string;
  email!: string;
  id!: number;
  isError: boolean = false;

  constructor(private tokenStorageService: TokenStorageService, public dataService: DataService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.dataService.condition$.subscribe();
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    this.dataService.findByRandomId()
        .then((vmd) => {
          this.id = vmd.id;
          this.isError = false;
        })
        .catch(err => {
          this.isError = true;
        });

    if (this.isLoggedIn) {
      let user = this.tokenStorageService.getUser();

      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
      this.email = user.email;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  setCondition(): void {
    this.dataService.changeCount(this.condition);
    this.condition = "";
  }
}
