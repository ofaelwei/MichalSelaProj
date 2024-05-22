import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventDTO } from '../models/event-dto';
import { PictureDTO } from '../models/picture-dto';
import { AppSessionDTO } from '../models/app-session-dto';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private apiUrl = 'http://54.163.58.248:3000/events'; 
  private picturesUrl = 'http://54.163.58.248:3000/pictures';
  private appSessionsUrl = 'http://54.163.58.248:3000/app-session';

  constructor(private http: HttpClient) { }

  getEvents(): Observable<EventDTO[]> {
    return this.http.get<EventDTO[]>(this.apiUrl);
  }

  getPictures(): Observable<PictureDTO[]> {
    return this.http.get<PictureDTO[]>(this.picturesUrl);
  }

  getAppSessions(): Observable<AppSessionDTO[]> {
    return this.http.get<AppSessionDTO[]>(this.appSessionsUrl);
  }
}
