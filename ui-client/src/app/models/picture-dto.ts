export interface PictureDTO {
    id: number;
    userId: string;
    timestamp: string;
    photo: {
      type: string;
      data: number[];
    };
  }