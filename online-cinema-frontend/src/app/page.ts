import {VideoMetadata} from "./video-metadata";

export class Page {

  constructor(public content: VideoMetadata[],
              public totalPages: number) {
  }
}
