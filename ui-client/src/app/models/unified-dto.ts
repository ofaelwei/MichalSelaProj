// unified-dto.ts
import { EventDTO } from './event-dto';
import { PictureDTO } from './picture-dto';

export type UnifiedDTO = {
  id: number;
  userId: string;
  timestamp: string;
  eventName?: string;
  eventTime?: string;
  photo?: string;
};
