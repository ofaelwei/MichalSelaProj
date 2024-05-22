import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Event } from './event.entity';

@Injectable()
export class EventsService {
  constructor(
    @InjectRepository(Event)
    private eventsRepository: Repository<Event>,
  ) {}

  create(event: Event): Promise<Event> {
    return this.eventsRepository.save(event);
  }

  createMany(events: Event[]): Promise<Event[]> {
    return this.eventsRepository.save(events);
  }

  findAll(): Promise<Event[]> {
    return this.eventsRepository.find();
  }
}
