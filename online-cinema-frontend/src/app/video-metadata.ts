import {Genre} from "./Genre";
import {Country} from "../../Country";

export class VideoMetadata {

  constructor(public id: number,
              public description: string,
              public contentType: string,
              public previewUrl: string,
              public streamUrl: string,
              public name: string,
              public year_filmed: number,
              public genreList: Genre[],
              public countryList: Country[]
              // private List<CrewWithRole> crewWithRole;
              ) {
  }
}
// previews: VideoMetadata[] = [];
// private String description;
// private MultipartFile file;
//
// private String name;
//
// private Integer year_filmed;
//
// /*Список жанров фильма*/
//
// private List<Genre> genreList;
//
// /*Страны производства фильма*/
//
// private List<Country> countryList;
//
// /*Съемочная группа*/
// private List<CrewWithRole> crewWithRole;
