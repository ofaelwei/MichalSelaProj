import { Controller, Get, Post, Body } from '@nestjs/common';
import { EventsService } from './events.service';
import { Event } from './event.entity';

@Controller('events')
export class EventsController {
  constructor(private readonly eventsService: EventsService) {}

  @Post()
  create(@Body() event: Event): Promise<Event> {
    return this.eventsService.create(event);
  }

  @Post('bulk')
  createMany(@Body() events: Event[]): Promise<Event[]> {
    return this.eventsService.createMany(events);
  }

  @Get()
  findAll(): Promise<Event[]> {
    return this.eventsService.findAll();
  }
}
