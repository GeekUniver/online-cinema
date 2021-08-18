import {Component,  OnInit} from '@angular/core';
import {DataService} from "../data.service";
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit  {
  condition: string ="";
  private roles!: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username!: string;

  constructor(private tokenStorageService: TokenStorageService, public dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.condition$.subscribe();
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
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
