import { Controller, Get, Post, Body } from '@nestjs/common';
import { AppSessionService } from './app-session.service';
import { AppSession } from './app-session.entity';

@Controller('app-session')
export class AppSessionController {
  constructor(private readonly appSessionService: AppSessionService) {}

  @Post()
  create(@Body() appSession: AppSession): Promise<AppSession> {
    return this.appSessionService.create(appSession);
  }

  @Post('bulk')
  createMany(@Body() appSessions: AppSession[]): Promise<AppSession[]> {
    return this.appSessionService.createMany(appSessions);
  }

  @Get()
  findAll(): Promise<AppSession[]> {
    return this.appSessionService.findAll();
  }
}
