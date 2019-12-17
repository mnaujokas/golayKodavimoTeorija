export interface IText {
  id?: number;
  data?: string;
  decoded?: string;
  probability?: number;
  noEncoding?: string;
}

export class Text implements IText {
  constructor(public id?: number, public data?: string, public decoded?: string, public probability?: number, public noEncoding?: string) {}
}
