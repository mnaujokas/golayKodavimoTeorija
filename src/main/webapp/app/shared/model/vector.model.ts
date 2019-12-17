export interface IVector {
  id?: number;
  data?: string;
  encoded?: string;
  transfered?: string;
  decoded?: string;
  probability?: number;
  errors?: string;
}

export class Vector implements IVector {
  constructor(
    public id?: number,
    public data?: string,
    public encoded?: string,
    public transfered?: string,
    public decoded?: string,
    public probability?: number,
    public errors?: string
  ) {}
}
