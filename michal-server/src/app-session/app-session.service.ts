import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { AppSession } from './app-session.entity';

@Injectable()
export class AppSessionService {
  constructor(
    @InjectRepository(AppSession)
    private appSessionRepository: Repository<AppSession>,
  ) {}

  create(appSession: AppSession): Promise<AppSession> {
    return this.appSessionRepository.save(appSession);
  }

  createMany(appSessions: AppSession[]): Promise<AppSession[]> {
    return this.appSessionRepository.save(appSessions);
  }

  findAll(): Promise<AppSession[]> {
    return this.appSessionRepository.find();
  }
}
