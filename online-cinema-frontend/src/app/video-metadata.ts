export class VideoMetadata {

  constructor(public id: number,
              public description: string,
              public contentType: string,
              public previewUrl: string,
              public streamUrl: string,
              public name: string,
              public year_filmed: number) {
  }
}
