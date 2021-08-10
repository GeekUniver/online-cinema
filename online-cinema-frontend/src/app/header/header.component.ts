import {Component,  OnInit} from '@angular/core';
import {DataService} from "../data.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit  {
  condition: string ="";

  constructor(public dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.condition$.subscribe();
  }

  setCondition(): void {
    this.dataService.changeCount(this.condition);
    this.condition = "";
  }
}
