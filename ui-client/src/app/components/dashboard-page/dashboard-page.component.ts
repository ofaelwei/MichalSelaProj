import { Component, OnInit } from '@angular/core';
import { EventDTO } from '../../models/event-dto';
import { PictureDTO } from '../../models/picture-dto';
import { AppSessionDTO } from '../../models/app-session-dto';
import { EventService } from '../../services/events.service';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';

interface UnifiedDTO {
  id: number;
  userId: string;
  timestamp: string;
  eventName?: string;
  eventTime?: string;
  photo?: string;
  appName?: string;
  startSession?: string;
  endSession?: string;
}

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css']
})
export class DashboardPageComponent implements OnInit {

  combinedItems: Observable<UnifiedDTO[]> | undefined;

  constructor(private eventService: EventService) { }

  ngOnInit() {
    const events$ = this.eventService.getEvents();
    const pictures$ = this.eventService.getPictures();
    const appSessions$ = this.eventService.getAppSessions();

    this.combinedItems = forkJoin([events$, pictures$, appSessions$]).pipe(
      map(([events, pictures, appSessions]) => {
        // Convert photo buffer to base64 string
        const unifiedPictures = pictures?.map((picture: PictureDTO) => ({
          id: picture.id,
          userId: picture.userId,
          timestamp: picture.timestamp,
          eventName: 'Picture',
          eventTime: picture.timestamp,
          photo: 'data:image/jpeg;base64,' + btoa(String.fromCharCode(...new Uint8Array(picture.photo.data)))
        })) ?? [];

        const unifiedEvents = events?.map((event: EventDTO) => ({
          ...event,
          timestamp: event.eventTime,
          eventTime: event.eventTime
        })) ?? [];

        const unifiedAppSessions = appSessions?.map((session: AppSessionDTO) => ({
          id: session.id,
          userId: session.userId,
          timestamp: session.startSession,
          eventName: 'App Session',
          appName: session.appName,
          startSession: session.startSession,
          endSession: session.endSession
        })) ?? [];

        const combined: UnifiedDTO[] = [
          ...unifiedPictures,
          ...unifiedEvents,
          ...unifiedAppSessions
        ];

        combined.sort((a, b) => new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime());
        return combined;
      })
    );
  }
}
