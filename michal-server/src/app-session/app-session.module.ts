import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppSessionService } from './app-session.service';
import { AppSessionController } from './app-session.controller';
import { AppSession } from './app-session.entity';

@Module({
  imports: [TypeOrmModule.forFeature([AppSession])],
  providers: [AppSessionService],
  controllers: [AppSessionController],
})
export class AppSessionModule {}
