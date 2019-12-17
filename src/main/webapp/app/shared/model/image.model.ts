export interface IImage {
  id?: number;
  fileContentType?: string;
  file?: any;
  noEncodingContentType?: string;
  noEncoding?: any;
  withEncodingContentType?: string;
  withEncoding?: any;
  probability?: number;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public fileContentType?: string,
    public file?: any,
    public noEncodingContentType?: string,
    public noEncoding?: any,
    public withEncodingContentType?: string,
    public withEncoding?: any,
    public probability?: number
  ) {}
}
