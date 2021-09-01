export class CommentRepr {

  constructor(public comment: string,
              public appUserId: number,
              public videoMetadataId: number,
              public appUserLogin: string,
              public data: string) {
  }
}
